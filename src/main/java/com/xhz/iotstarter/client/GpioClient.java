package com.xhz.iotstarter.client;

import com.xhz.iotstarter.enums.GpioBuildEnum;
import com.xhz.iotstarter.enums.IotDeviceEnum;
import lombok.Data;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Map;

/**
 * @Author: xuhongzhuo
 * @Date: 2022/2/12 1:08 PM
 * <p>
 * 当前的gpioClient
 * 里面记载了整个系统用到的传感器序列，初始化方式，以及其他的配置
 */
@Data
public class GpioClient {

    /**
     * gpio 端口的初始化方式
     */
    private GpioBuildEnum buildMethod;

    /**
     * Iot设备的gpio口和设备的映射  一般是 dht11 : 1 这种形式的
     */
    private Map<IotDeviceEnum, Integer> iotPinMap;

    /**
     * Iot设备不一定只有一个GPIO口，而是可能一个传感器需要许多GPIO口
     */
    private Map<IotDeviceEnum, List<Integer>> iotDevicePinsMap;

}
