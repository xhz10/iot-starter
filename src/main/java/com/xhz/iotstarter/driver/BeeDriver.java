package com.xhz.iotstarter.driver;

import com.pi4j.library.pigpio.PiGpio_PWM;
import com.pi4j.wiringpi.Gpio;
import com.xhz.iotstarter.enums.IotDeviceEnum;
import com.xhz.iotstarter.music.Note;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Author: xuhongzhuo
 * @Date: 2022/3/13 9:22 PM
 */
@Component
@Slf4j
public class BeeDriver extends BaseDriver {

    private boolean status = false;

    private void initBeep() {
        // 设置针脚为pwm输出模式
        Gpio.pinMode(getPin(), Gpio.PWM_OUTPUT);

        // 设置为ms模式
        Gpio.pwmSetMode(Gpio.PWM_MODE_MS);

        // 设置时钟基础频率为19.2M/32=600KHZ
        Gpio.pwmSetClock(20000);
        status = true;
    }

    private void beep(int freq, double ms) {
        if (!status) {
            initBeep();
        }
        int range;
        /*if (freq < 2000 || freq > 5000) {
            log.info("invalid freq");
            return;
        }*/
        //设置range为 600KHZ/freq。也就是由range个1/600KHZ组成了freq频率的周期。
        range = 600000 / freq;
        Gpio.pwmSetRange(range);
        //设置占空比为50%。
        Gpio.pwmWrite(getPin(), range / 2);
        if (ms > 0) {
            Gpio.delay((long) ms);
        }
    }

    public void playMusic(List<Note> music) {
        if (CollectionUtils.isEmpty(music)) {
            log.error("播放个锤子，都没音乐");
            return;
        }
        for (int i = 0; i < music.size(); i++) {
            beep(music.get(i).getNote().getNumber(), music.get(i).getOneset());
            Gpio.pwmWrite(getPin(), 0);
            //Gpio.delay(100);
        }
        Gpio.pwmWrite(getPin(), 0);
    }


    @Override
    public void initDevice() {
        init(IotDeviceEnum.BEE, getIotProperties());
    }
}

