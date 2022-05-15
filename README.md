# 介绍一下iot-starter

SDK一开始不支持单个传感器多个GPIO引脚操作，但是已经被更新，现在可以支持单个传感器的多个GPIO操作，只需要加入如下配置文件（RGB灯为例）
```yaml
# iot传感器操作
iot:
  iot-device-pins-map:
    RGB_LED:
      - 1
      - 4
      - 5
  ad: true
  build-method: wiringpisetup
```
**当然我这个行为主要是为了兼容过去的单个GPIO的驱动可用，后续会逐渐更新驱动。**
## 1. 如何使用

首先新建一个SpringBoot项目

![输入图片说明](https://images.gitee.com/uploads/images/2022/0513/194006_467a43a4_5560406.png "截屏2022-05-13 下午12.03.29.png")


其次写入配置文件application.yaml

```yaml
# iot传感器操作
iot:
  ad: true # 是否开启AD转换，如果开启默认是pcf8591的使用
  pcf8591: 0x48 # pcf8591默认是0X48也可以自己定义
  iotPinMap: # 如果一个传感器只希望对应一个GPIO可以采用这套方案
    DHT11: 4 
    FLAME: 5
  iot-device-pins-map: # 如果一个传感器希望对应多个GPIO需要采用这个方案
    RGB_LED:
      - 1
      - 4
      - 5
  build-method: wiringpisetup
  # gpio的初始化有很多策略，分别是
  # wiringpisetup : 该函数使用WiringPi编号方式对树莓派引脚进行初始化
  # wiringPiSetupGpio : 该函数使用BCM编号方式对树莓派引脚进行初始化
  # wiringPiSetupPhys : 该函数使用物理编号方式对树莓派引脚进行初始化
  # wiringPiSetupSys : 该函数与wiringPiSetupGpio类似，也是采用BCM编号方式对树莓派引脚进行初始化。所不同的是，该函数并不是访问实际的硬件，而是对/sys/class/gpio接口进行操作
```

最后来到一个类中直接使用驱动即可例如

![输入图片说明](https://images.gitee.com/uploads/images/2022/0513/193755_f238bb22_5560406.png "截屏2022-05-13 下午7.18.51.png")

运行结果就是这里面的数据是正确的，改日附上截图

## 2. 驱动扩展

本项目如何使用驱动不是重点，重点是如何扩展驱动并且顺利集成到SDK中，具体操作如下

1. 首先在枚举类中加上自己传感器的自定义枚举

![输入图片说明](https://images.gitee.com/uploads/images/2022/0513/193815_57862a3a_5560406.png "截屏2022-05-13 下午7.24.14.png")

2. 定义驱动类并且继承BaseDriver

![输入图片说明](https://images.gitee.com/uploads/images/2022/0513/193832_ef99f59e_5560406.png "截屏2022-05-13 下午7.26.21.png")

3. 初始化驱动,重写initDevice方法，我们从下图可以看到初始化的东西就是我们之前枚举类中定义的

![输入图片说明](https://images.gitee.com/uploads/images/2022/0513/193850_e362ad4a_5560406.png "截屏2022-05-13 下午7.27.23.png")

4. 编写自己的驱动即可（用到了wiringpi库）附上dht11驱动代码，可以用来参考

```java
package com.xhz.iotstarter.driver;

import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.GpioUtil;
import com.xhz.iotstarter.client.GpioClient;
import com.xhz.iotstarter.config.prop.IotProperties;
import com.xhz.iotstarter.enums.IotDeviceEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @Author: xuhongzhuo
 * @Date: 2022/2/10 1:33 PM
 * <p>
 * dht11 的驱动代码
 */
@Component
@Slf4j
public class Dht11Driver extends BaseDriver {

    private static final int MAXTIMINGS = 85;
    private final int[] dht11_dat = {0, 0, 0, 0, 0};


    /**
     * 测量温湿度程序 必须保证同一时刻只有一个线程在做这件事
     */
    private synchronized float[] getTemperatureAndHumidity() {
        int pin = getPin();
        log.info("pin is: " + pin);
        if (!isExec()) {
            // 设备没准备好呢
            log.info("数据还没准备好呢");
            return new float[]{255};
        }
        int laststate = Gpio.HIGH;
        int j = 0;
        dht11_dat[0] = dht11_dat[1] = dht11_dat[2] = dht11_dat[3] = dht11_dat[4] = 0;

        Gpio.pinMode(pin, Gpio.OUTPUT);
        Gpio.digitalWrite(pin, Gpio.LOW);
        Gpio.delay(18);

        Gpio.digitalWrite(pin, Gpio.HIGH);
        Gpio.pinMode(pin, Gpio.INPUT);

        for (int i = 0; i < MAXTIMINGS; i++) {
            int counter = 0;
            while (Gpio.digitalRead(pin) == laststate) {
                counter++;
                Gpio.delayMicroseconds(1);
                if (counter == 255) {
                    break;
                }
            }

            laststate = Gpio.digitalRead(pin);

            if (counter == 255) {
                break;
            }

            /* ignore first 3 transitions */
            if (i >= 4 && i % 2 == 0) {
                /* shove each bit into the storage bytes */
                dht11_dat[j / 8] <<= 1;
                if (counter > 16) {
                    dht11_dat[j / 8] |= 1;
                }
                j++;
            }
        }
        // check we read 40 bits (8bit x 5 ) + verify checksum in the last
        // byte
        if (j >= 40 && checkParity()) {
            float h = (float) ((dht11_dat[0] << 8) + dht11_dat[1]) / 10;
            if (h > 100) {
                h = dht11_dat[0]; // for DHT11
            }
            float c = (float) (((dht11_dat[2] & 0x7F) << 8) + dht11_dat[3]) / 10;
            if (c > 125) {
                c = dht11_dat[2]; // for DHT11
            }
            if ((dht11_dat[2] & 0x80) != 0) {
                c = -c;
            }
            final float f = c * 1.8f + 32;
            float[] result = new float[2];
            result[0] = h;
            result[1] = c;
            return result;
        } else {
            float[] result = new float[2];
            result[0] = -1;
            result[1] = -1;
            return new float[2];
        }

    }

    private boolean checkParity() {
        return dht11_dat[4] == (dht11_dat[0] + dht11_dat[1] + dht11_dat[2] + dht11_dat[3] & 0xFF);
    }


    /**
     * 只获取温度
     *
     * @return
     */
    public float getTemperature() throws Exception {
        for(int i=0;i<20;i++) {
            Thread.sleep(2000);
            float[] temperatureAndHumidity = getTemperatureAndHumidity();
            /**
             * 考虑到健壮性等因素，需要明确好多条件
             */

            if (!isExec() || temperatureAndHumidity.length == 0 || temperatureAndHumidity.length == 1 || temperatureAndHumidity[0] == 255 || temperatureAndHumidity[1] == 255) {
                continue;
            } else {
                return temperatureAndHumidity[1];
            }
        }
        throw new Exception("数据测量失败");
    }

    /**
     * 只获取湿度
     *
     * @return
     */
    public float getHumidity() throws Exception {
        for (int i = 0; i < 20; i++) {
            Thread.sleep(2000);
            float[] temperatureAndHumidity = getTemperatureAndHumidity();
            if (!isExec() || temperatureAndHumidity.length == 0 || temperatureAndHumidity.length == 1 || temperatureAndHumidity[0] == 255 || temperatureAndHumidity[1] == 255) {
                continue;
            } else {
                return temperatureAndHumidity[0];
            }
        }
        throw new Exception("数据测量失败");
    }

    /**
     * 同时获取温湿度
     *
     * @return
     */
    public float[] getTemAndHum() throws Exception {
        for (int i = 0; i < 20; i++) {
            Thread.sleep(2000);
            float[] temperatureAndHumidity = getTemperatureAndHumidity();
            if (!isExec() || temperatureAndHumidity.length == 0 || temperatureAndHumidity.length == 1 || temperatureAndHumidity[0] == 255 || temperatureAndHumidity[1] == 255) {
                continue;
            } else {
                return temperatureAndHumidity;
            }
        }
        throw new Exception("数据测量失败");
    }


    @Override
    public void initDevice() {
        init(IotDeviceEnum.DHT11, getIotProperties());
    }
}

```

5. 附上使用了pcf8591的火焰传感器的驱动

```java
package com.xhz.iotstarter.driver;

import com.pi4j.wiringpi.Gpio;
import com.xhz.iotstarter.enums.IotDeviceEnum;
import com.xhz.iotstarter.enums.PCF8951IOEnum;
import com.xhz.iotstarter.exceptions.DeviceInitException;
import com.xhz.iotstarter.i2c.PCF8591;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author: xuhongzhuo
 * @Date: 2022/2/28 10:39 AM
 */
@Component
@Slf4j
public class FlameDriver extends BaseDriver {

    @Autowired
    private PCF8591 pcf8591;

    /**
     * 只读取一次
     *
     * @return 1代表有，0代表无
     */
    public synchronized int getFlameStatus(PCF8951IOEnum input) throws DeviceInitException {
        return pcf8591.readInput(input);
    }

    /**
     * 返回火焰大小等级
     * 255 相安无事 -1
     * 200-255 轻度 4级
     * 150-200 中度 3级
     * 100-150 重度 2级
     * 0-100   恐怖 1级
     *
     * @param input
     * @return
     */
    public int getFlameLevel(PCF8951IOEnum input) throws DeviceInitException {
        int status = -1;
        for (int i = 0; i < 5; i++) {
            status = getFlameStatus(input);
            if (status != 255)
                break;
            Gpio.delay(1000);
        }
        if(status == 255) {
            return -1;
        } else if(status >= 200) {
            return 4;
        } else if(status >= 150) {
            return 3;
        } else if (status >= 100) {
            return 2;
        } else if(status >= 0) {
            return 1;
        } else {
            return -1;
        }
    }


    /**
     * 初始化设备
     */
    @Override
    public void initDevice() {
        init(IotDeviceEnum.FLAME, getIotProperties());
    }
}
```

## 3. 其他好玩的

因为我玩过蜂鸣器，我这里面用到了一个B站UP主分享的蜂鸣器的频率对应的音乐符号，所以在这里music包我全部注释上了，附上链接 https://blog.csdn.net/m0_64206746/article/details/122626565?utm_source=app&app_version=4.18.0&code=app_1562916241&uLinkId=usr1mkqgl919blen

如果想玩就把注释去掉就好。

