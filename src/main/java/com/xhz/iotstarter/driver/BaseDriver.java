package com.xhz.iotstarter.driver;

import com.xhz.iotstarter.config.prop.IotProperties;
import com.xhz.iotstarter.enums.IotDeviceEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: xuhongzhuo
 * @Date: 2022/2/27 6:55 PM
 */
@Slf4j
@Component
public class BaseDriver extends AbstractDriver {

    @Autowired
    private IotProperties iotProperties;


    protected IotProperties getIotProperties() {
        return iotProperties;
    }

    @Override
    public void initDevice() {
        log.info("base驱动初始化");
    }
}
