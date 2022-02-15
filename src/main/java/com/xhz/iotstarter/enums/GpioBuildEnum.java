package com.xhz.iotstarter.enums;

/**
 * @Author: xuhongzhuo
 * @Date: 2022/2/10 2:48 PM
 * <p>
 * 构建gpio的枚举类型
 */
public enum GpioBuildEnum {

    /**
     * wiringpi 初始化方式
     */
    WIRINGPISETUP,
    /**
     * bcm 管脚
     */
    BCMSETUP,
    /**
     * 物理管脚
     */
    PHYSETUP,

    /**
     * 系统sys管脚
     */
    SYSSETUP
}
