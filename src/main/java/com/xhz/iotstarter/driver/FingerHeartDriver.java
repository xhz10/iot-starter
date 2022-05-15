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

    private double step = 0.75;

    // 利用到了pcf8591模块
    @Autowired
    private PCF8591 pcf8591;


    public synchronized double getHeartInfo(PCF8951IOEnum input, double oldValue) throws DeviceInitException {
        int heartStatus = getHeartStatus(input);
        return heartStatus;//这个平滑就是取本次和上一次测量数据的加权平均值
    }

    /**
     * 获取指定时间内的心跳数据
     *
     * @param input
     * @param count 秒数
     * @return
     */
    public double[] getTimesHeartInfo(PCF8951IOEnum input, int count) throws Exception {
        if(count == 0) {
            throw new Exception("秒数时间最少为1秒");
        }
        double ans[] = new double[count];
        double old = 0;
        for (int i = 0; i < count; i++) {
            double heartInfo = getHeartInfo(input, old);
            old = heartInfo;
            ans[i] = heartInfo;
            Gpio.delay(1000);
        }
        return ans;
    }

    /**
     * 获取心跳电压
     *
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
