package com.xhz.iotstarter.exceptions;

/**
 * @Author: xuhongzhuo
 * @Date: 2022/3/13 4:00 PM
 */
public class DeviceInitException extends Exception {

    public DeviceInitException() {
        super("设备初始化异常，请检查设备是否完善");
    }
}
