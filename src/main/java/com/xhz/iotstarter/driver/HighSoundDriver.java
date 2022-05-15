package com.xhz.iotstarter.driver;

import com.pi4j.wiringpi.Gpio;
import com.xhz.iotstarter.enums.IotDeviceEnum;
import com.xhz.iotstarter.enums.PCF8951IOEnum;
import com.xhz.iotstarter.exceptions.DeviceInitException;
import com.xhz.iotstarter.i2c.PCF8591;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: xuhongzhuo
 * @Date: 2022/4/26 12:42 PM
 *
 * 高敏感麦克风（模拟声音属于是）
 */
@Slf4j
@Component
public class HighSoundDriver extends BaseDriver {

    @Autowired
    private PCF8591 pcf8591;

    public synchronized int getSoundStatus(PCF8951IOEnum input) throws DeviceInitException {
        return pcf8591.readInput(input);
    }

    @Override
    public void initDevice() {
        init(IotDeviceEnum.HIGHSOUND, getIotProperties());
    }
}
