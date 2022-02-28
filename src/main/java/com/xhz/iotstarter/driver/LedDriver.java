package com.xhz.iotstarter.driver;

import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.GpioUtil;
import com.xhz.iotstarter.client.GpioClient;
import com.xhz.iotstarter.config.prop.IotProperties;
import com.xhz.iotstarter.enums.IotDeviceEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: xuhongzhuo
 * @Date: 2022/2/12 1:32 PM
 */
@Component
public class LedDriver extends BaseDriver {


    /**
     * 小灯亮的API
     * @return
     */
    public void turnOn() {
        Gpio.pinMode(getPin(), Gpio.OUTPUT);
        int pin = getPin();
        Gpio.digitalWrite(pin, Gpio.HIGH);
    }


    /**
     * 小灯灭的API
     * @return
     */
    public void turnOff() {
        Gpio.pinMode(getPin(), Gpio.OUTPUT);
        int pin = getPin();
        Gpio.digitalWrite(pin, Gpio.LOW);
    }

    @Override
    public void initDevice() {
        init(IotDeviceEnum.LED_RED_GREEN, getIotProperties());
        /**
         * 统一设置成out
         */

    }
}
