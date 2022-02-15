package com.xhz.iotstarter.driver;

import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.GpioUtil;
import com.xhz.iotstarter.client.GpioClient;
import com.xhz.iotstarter.config.prop.IotProperties;
import com.xhz.iotstarter.enums.IotDeviceEnum;
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
public class Dht11Driver extends AbstractDriver implements InitializingBean {

    private static final int MAXTIMINGS = 85;
    private final int[] dht11_dat = {0, 0, 0, 0, 0};



    @Autowired
    private IotProperties iotProperties;


    /**
     * 测量温湿度程序 必须保证同一时刻只有一个线程在做这件事
     */
    private synchronized float[] getTemperatureAndHumidity(final int pin) {
        if (!isExec()) {
            // 设备没准备好呢
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
            float[] res = new float[2];
            res[0] = h; // 湿度
            res[1] = c; // 温度
            return res;
        } else {
            return new float[]{255};
        }

    }

    /**
     * 只获取温度
     *
     * @param pin
     * @return
     */
    public float getTemperature(int pin) {
        float[] temperatureAndHumidity = getTemperatureAndHumidity(pin);
        /**
         * 考虑到健壮性等因素，需要明确好多条件
         */
        if (!isExec() || temperatureAndHumidity.length == 0 || temperatureAndHumidity.length == 1 || temperatureAndHumidity[0] == 255 || temperatureAndHumidity[1] == 255) {
            return 0.0F;
        } else {
            return temperatureAndHumidity[1];
        }
    }

    /**
     * 只获取湿度
     *
     * @param pin
     * @return
     */
    public float getHumidity(int pin) {
        float[] temperatureAndHumidity = getTemperatureAndHumidity(pin);
        if (!isExec() || temperatureAndHumidity.length == 0 || temperatureAndHumidity.length == 1 || temperatureAndHumidity[0] == 255 || temperatureAndHumidity[1] == 255) {
            return 0.0F;
        } else {
            return temperatureAndHumidity[0];
        }
    }

    /**
     * 同时获取温湿度
     *
     * @param pin
     * @return
     */
    public float[] getTemAndHum(int pin) {
        float[] temperatureAndHumidity = getTemperatureAndHumidity(pin);
        if (!isExec() || temperatureAndHumidity.length == 0 || temperatureAndHumidity.length == 1 || temperatureAndHumidity[0] == 255 || temperatureAndHumidity[1] == 255) {
            return new float[]{255};
        } else {
            return temperatureAndHumidity;
        }
    }


    private boolean checkParity() {
        return dht11_dat[4] == (dht11_dat[0] + dht11_dat[1] + dht11_dat[2] + dht11_dat[3] & 0xFF);
    }

    /**
     * bean 初始化的时候去拿到dht11 的 pin 口
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        init(IotDeviceEnum.DHT11, iotProperties);
    }
}
