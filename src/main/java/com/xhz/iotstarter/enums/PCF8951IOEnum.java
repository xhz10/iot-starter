package com.xhz.iotstarter.enums;

/**
 * @Author: xuhongzhuo
 * @Date: 2022/3/13 3:48 PM
 */
public enum PCF8951IOEnum {


    AINO(0x40, 1),
    AIN1(0x41, 1),
    AIN2(0x42, 1),
    AIN3(0x43, 1),

    // AOUT(0xxx,0),

    EMPTY(-1, -1);

    private int number;

    /**
     * 1-输入
     * 0-输出
     */
    private int type;


    PCF8951IOEnum(int number, int type) {
        this.number = number;
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public int getType() {
        return type;
    }
}
