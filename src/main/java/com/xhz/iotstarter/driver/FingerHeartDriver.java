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
 * @Date: 2022/5/15 11:44 AM
 * <p>
 * 手指测试心跳驱动
 */
@Component
@Slf4j
public class FingerHeartDriver extends BaseDriver {


    // 利用到了pcf8591模块
    @Autowired
    private PCF8591 pcf8591;

/*    public int[] getHeartInfo(PCF8951IOEnum input) throws DeviceInitException {
        int heartStatus = getHeartStatus(input);
    }*/

    /**
     * 获取心跳电压
     * @param input
     * @return
     * @throws DeviceInitException
     */
    public synchronized int getHeartStatus(PCF8951IOEnum input) throws DeviceInitException {
        return pcf8591.readInput(input);
    }

    @Override
    public void initDevice() {
        // 定义传感器驱动为FINGER_HEART
        init(IotDeviceEnum.FINGER_HEART, getIotProperties());
    }
}
