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
