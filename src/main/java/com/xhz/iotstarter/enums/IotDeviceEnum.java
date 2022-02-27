package com.xhz.iotstarter.enums;

/**
 * @Author: xuhongzhuo
 * @Date: 2022/2/12 12:44 PM
 */
public enum IotDeviceEnum {

    DHT11("dht11", 0),
    LED_RED_GREEN("ledRedGreen", 1),
    FLAME("flame", 2),


    EMPTY("base", -1);

    private String iotName;
    private int iotId;

    IotDeviceEnum(String iotName, int iotId) {
        this.iotName = iotName;
        this.iotId = iotId;
    }

    public String getIotName() {
        return iotName;
    }

    public int getIotId() {
        return iotId;
    }

}
