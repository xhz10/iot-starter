package com.xhz.iotstarter.exceptions;

/**
 * @Author: xuhongzhuo
 * @Date: 2022/2/10 2:54 PM
 */
public class GpioBuildException extends Exception{

    public GpioBuildException() {
        super("gpio 构建失败");
    }
}
