package com.xhz.iotstarter.driver;

import com.xhz.iotstarter.client.GpioClient;
import com.xhz.iotstarter.config.prop.IotProperties;
import com.xhz.iotstarter.enums.IotDeviceEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: xuhongzhuo
 * @Date: 2022/2/12 1:32 PM
 */
@Component
public class LedDriver extends BaseDriver {

    @Override
    public void initDevice() {
        init(IotDeviceEnum.LED_RED_GREEN, getIotProperties());
    }
}