package com.xhz.iotstarter.config;

import com.xhz.iotstarter.client.GpioClient;
import com.xhz.iotstarter.config.condition.IotCondition;
import com.xhz.iotstarter.config.prop.IotProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;


/**
 * @Author: xuhongzhuo
 * @Date: 2022/2/10 12:59 PM
 */
@SpringBootConfiguration
@EnableConfigurationProperties(IotProperties.class)
@ConditionalOnClass(IotCondition.class)
public class IotAutoConfiguration {

    @Autowired
    private IotProperties iotProperties;

    @Bean
    @Conditional(IotCondition.class)
    public GpioClient gpioClient() {
        GpioClient gpioClient = new GpioClient();
        gpioClient.setIotPinMap(iotProperties.getIotPinMap());
        gpioClient.setBuildMethod(iotProperties.getBuildMethod());
        gpioClient.setIotDevicePinsMap(iotProperties.getIotDevicePinsMap());
        return gpioClient;
    }
}
