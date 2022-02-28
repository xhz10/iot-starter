package com.xhz.iotstarter.driver;

import com.pi4j.wiringpi.Gpio;
import com.xhz.iotstarter.enums.IotDeviceEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author: xuhongzhuo
 * @Date: 2022/2/28 10:39 AM
 */
@Component
@Slf4j
public class FlameDriver extends BaseDriver {


    /**
     * 只读取一次
     *
     * @return 1代表有，0代表无
     */
    public synchronized int getFlameStatus() {
        Gpio.pinMode(getPin(), Gpio.INPUT);
        int pin = getPin();
        return pin != -1 ? Gpio.digitalRead(pin) : -1;
    }


    /**
     * 根据单位时间和次数和等待数查询
     *
     * @param timeUnit 时间单位，只支持微秒和毫秒
     * @param count    sleep多少timeunit
     * @param times    测量多少次取结果
     * @return
     * @throws Exception timeunit不支持的时候抛出异常
     */
    public int getFlameStatusByTimeUnit(TimeUnit timeUnit, long count, int times) throws Exception {
        /**
         * 毫秒级别
         */
        if (TimeUnit.MILLISECONDS.equals(timeUnit)) {
            for (int i = 0; i < times; i++) {
                if (getFlameStatus() == Gpio.HIGH) {
                    return 0;
                }
                Gpio.delay(count);
            }
            return 1;
        } else if (TimeUnit.MICROSECONDS.equals(timeUnit)) {// 微秒级别的
            for (int i = 0; i < times; i++) {
                if (getFlameStatus() == 0) {
                    return 0;
                }
                Gpio.delayMicroseconds(count);
            }
            return 1;
        } else {
            throw new Exception("不支持的时间单位");
        }
    }

    /**
     * 初始化设备
     */
    @Override
    public void initDevice() {
        init(IotDeviceEnum.FLAME, getIotProperties());
        // 统一是input模式

    }
}
