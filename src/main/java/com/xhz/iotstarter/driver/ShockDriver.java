package com.xhz.iotstarter.driver;

import com.pi4j.wiringpi.Gpio;
import com.xhz.iotstarter.enums.IotDeviceEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: xuhongzhuo
 * @Date: 2022/4/26 11:36 AM
 */
@Component
@Slf4j
public class ShockDriver extends BaseDriver{


    public void doSome() {
        // 设置为输出gpio
        Gpio.pinMode(getPin(), Gpio.INPUT);
    }

    /**
     * 初始化shock的GPIO口
     */
    @Override
    public void initDevice() {
        init(IotDeviceEnum.SHOCK, getIotProperties());
    }
}
