package com.xhz.iotstarter.driver;

import com.xhz.iotstarter.client.GpioClient;
import com.xhz.iotstarter.config.prop.IotProperties;
import com.xhz.iotstarter.enums.IotDeviceEnum;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @Author: xuhongzhuo
 * @Date: 2022/2/10 1:36 PM
 * <p>
 * 所有驱动初始化的父类
 */
public class AbstractDriver {

    /**
     * dht11 的gpio口
     */
    private int pin = -1;

    /**
     * 驱动是否初始化完毕的标记位
     */
    private boolean exec = false;

    /**
     * iot设备
     */
    protected IotDeviceEnum device;


    protected void setExec(boolean exec) {
        this.exec = exec;
    }

    protected void setPin(int pin) {
        this.pin = pin;
    }

    public int getPin() {
        return pin;
    }

    public IotDeviceEnum getDevice() {
        return device;
    }

    public void setDevice(IotDeviceEnum device) {
        this.device = device;
    }

    public boolean isExec() {
        return exec;
    }

    public String getDeviceName() {
        return device != null ? device.getIotName() : "未知设备";
    }

    public int getDeviceId() {
        return device != null ? device.getIotId() : -1;
    }

    public boolean checkDevice() {
        IotDeviceEnum[] values = IotDeviceEnum.values();
        for(IotDeviceEnum iot : values) {
            if (iot.equals(device)) {
                exec = true;
                return true;
            }
        }
        exec = false;
        return false;
    }

    public void init(IotDeviceEnum device, IotProperties iotProperties) {
        /**
         * 获取到iot设备的map
         */
        setDevice(device);
        Map<IotDeviceEnum, Integer> iotPinMap = iotProperties.getIotPinMap();
        // 如果设备已经注册了并且pin不是-1
        System.out.println(iotPinMap);
        if (!CollectionUtils.isEmpty(iotPinMap) && iotPinMap.containsKey(getDevice()) && iotPinMap.get(getDevice()) != -1) {
            setPin(iotPinMap.get(getDevice()));
            setExec(true);
        }
        System.out.println(getDeviceName() + "导出pin口 : " + getPin());
        // GpioUtil.export(getPin(), GpioUtil.DIRECTION_OUT);
    }
}
