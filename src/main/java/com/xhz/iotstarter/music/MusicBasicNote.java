package com.xhz.iotstarter.music;

/**
 * @Author: xuhongzhuo
 * @Date: 2022/3/14 12:49 PM
 * <p>
 * 设置时钟基础频率为19.2M/32=600KHZ
 * Gpio.pwmSetClock(32);
 */
public enum MusicBasicNote {
    DO(2093),
    RE(2349),
    MI(2637),
    FA(2794),
    SO(3136),
    LA(3520),
    XI(3951),
    DO1(4186),
    RI1(4698),

    //ONESET(500),

    EMPTY(-1);
    private int number;

    MusicBasicNote(int i) {
        this.number = i;
    }

    public int getNumber() {
        return number;
    }
}
