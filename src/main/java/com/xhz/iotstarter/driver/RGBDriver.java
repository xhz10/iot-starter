package com.xhz.iotstarter.driver;

import com.pi4j.wiringpi.Gpio;
import com.xhz.iotstarter.enums.IotDeviceEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: xuhongzhuo
 * @Date: 2022/5/15 3:24 PM
 * <p>
 * 考虑到整体原因，如果每个GPIO口都有独立意义的话
 * 再RGB中独立意义分别是[R,G,B],否则全部视作无效
 */
@Component
@Slf4j
public class RGBDriver extends BaseDriver {

    private int R = -1;

    private int G = -1;

    private int B = -1;

    /**
     * 是否初始化了，就初始化一次
     */
    private boolean init = false;


    /**
     * 小灯闪烁多少毫秒
     *
     * @param pin   RGB的某一个PIN
     * @param count 毫秒数
     * @return
     */
    public synchronized boolean flashing(int pin, long count) {
        if (init) {
            flash(pin, count);
            return true;
        } else if (initRGB()) {
            flash(pin, count);
            return true;
        }
        return false;
    }

    public void RGBFlashingLoop(long count) {
        while (true) {
            if (!flashing(R, count)) {
                break;
            }
            if (!flashing(G, count)) {
                break;
            }
            if (!flashing(B, count)) {
                break;
            }
        }
    }

    /**
     * 小灯闪烁多少毫秒
     *
     * @param pin   RGB的某一个PIN
     * @param count 毫秒数
     * @return
     */
    private synchronized void flash(int pin, long count) {
        Gpio.digitalWrite(pin, Gpio.HIGH);
        Gpio.delay(count);
        Gpio.digitalWrite(pin, Gpio.LOW);
    }

    /**
     * 初始化RGB这个东西
     *
     * @return
     */
    private synchronized boolean initRGB() {
        if (isExec()) {
            Gpio.pinMode(R, Gpio.OUTPUT);
            Gpio.pinMode(G, Gpio.OUTPUT);
            Gpio.pinMode(B, Gpio.OUTPUT);
            this.init = true;
            return true;
        }
        return false;
    }

    @Override
    public void initDevice() {
        init(IotDeviceEnum.RGB_LED, getIotProperties());
        List<Integer> pins = getPins();
        if (pins.size() <= 4) {
            // 这个驱动谁也不行用
            setExec(false);
            log.error("这个驱动初始化绝对有问题，最基础的RGB不全");
            return;
        }
        // 初始化RGB的GPIO口
        R = pins.get(0);
        G = pins.get(1);
        B = pins.get(2);
    }
}
