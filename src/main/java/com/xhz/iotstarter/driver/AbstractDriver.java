package com.xhz.iotstarter.driver;

import com.pi4j.wiringpi.GpioUtil;
import com.xhz.iotstarter.client.GpioClient;
import com.xhz.iotstarter.config.prop.IotProperties;
import com.xhz.iotstarter.enums.IotDeviceEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: xuhongzhuo
 * @Date: 2022/2/10 1:36 PM
 * <p>
 * 所有驱动初始化的父类
 */
@Slf4j
public abstract class AbstractDriver implements InitializingBean {


    /**
     * gpio口
     */
    private int pin = -1;

    private List<Integer> pins = new ArrayList<>();

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

    public List<Integer> getPins() {
        return pins;
    }

    public void setPins(List<Integer> pins) {
        this.pins = pins;
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
        if (!CollectionUtils.isEmpty(iotPinMap) && iotPinMap.containsKey(getDevice()) && iotPinMap.get(getDevice()) != -1) {
            setPin(iotPinMap.get(getDevice()));
            setExec(true);
        }
        log.info(getClass().getSimpleName() + ": " +getDeviceName() + "导出pin口 : " + getPin());
        if(isExec()) {
            GpioUtil.export(getPin(), GpioUtil.DIRECTION_OUT);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initDevice();
    }

    public abstract void initDevice();
}
