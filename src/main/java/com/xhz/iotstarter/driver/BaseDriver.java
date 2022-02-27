package com.xhz.iotstarter.driver;

import com.xhz.iotstarter.config.prop.IotProperties;
import com.xhz.iotstarter.enums.IotDeviceEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: xuhongzhuo
 * @Date: 2022/2/27 6:55 PM
 */
@Component
public class BaseDriver extends AbstractDriver {

    @Autowired
    private IotProperties iotProperties;


    protected IotProperties getIotProperties() {
        return iotProperties;
    }

    @Override
    public void initDevice() {
        init(IotDeviceEnum.EMPTY, iotProperties);
    }
}
