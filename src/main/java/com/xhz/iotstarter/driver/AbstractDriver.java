package com.xhz.iotstarter.driver;

import com.pi4j.wiringpi.GpioUtil;
import com.xhz.iotstarter.config.prop.IotProperties;
import com.xhz.iotstarter.enums.IotDeviceEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
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
     * 是否扩展GPIO口
     */
    private boolean ext = false;

    /**
     * iot设备
     */
    protected IotDeviceEnum device;


    protected boolean isExt() {
        return ext;
    }

    protected void setExt(boolean ext) {
        this.ext = ext;
    }

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
        for (IotDeviceEnum iot : values) {
            if (iot.equals(device)) {
                exec = true;
                return true;
            }
        }
        exec = false;
        return false;
    }

    /**
     * 这里暂时粗糙一点，以后再想办法优化
     * @param device
     * @param iotProperties
     */
    public void init(IotDeviceEnum device, IotProperties iotProperties) {
        /**
         * 获取到iot设备的map
         */
        setDevice(device);
        Map<IotDeviceEnum, Integer> iotPinMap = iotProperties.getIotPinMap();
        Map<IotDeviceEnum, List<Integer>> iotDevicePinsMap = iotProperties.getIotDevicePinsMap();
        if (!CollectionUtils.isEmpty(iotDevicePinsMap) &&iotDevicePinsMap.containsKey(device)) {
            // 说明开启了扩展操作 那么即使配置文件配置了之前的也失效了
            setPins(iotDevicePinsMap.get(device));
            setExec(true);
            setExt(true);
            log.info(getClass().getSimpleName() + ": " + getDeviceName() + "导出pin口 : " + getPins().toArray());
        } else if (!CollectionUtils.isEmpty(iotPinMap) && iotPinMap.containsKey(getDevice()) && iotPinMap.get(getDevice()) != -1) {
            setPin(iotPinMap.get(getDevice()));
            setExec(true);
            log.info(getClass().getSimpleName() + ": " + getDeviceName() + "导出pin口 : " + getPin());
        }
        if (isExec() && !isExt()) {
            GpioUtil.export(getPin(), GpioUtil.DIRECTION_OUT);
        } else if (isExec() && isExt()) {
            List<Integer> pins = getPins();
            for (int i = 0; i < pins.size(); i++) {
                GpioUtil.export(pins.get(i), GpioUtil.DIRECTION_OUT);
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(isExt()) {
            log.info("这是一个扩展的方案");
        }
        initDevice();
    }

    public abstract void initDevice();
}
