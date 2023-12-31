package com.xhz.iotstarter.config.prop;

import com.xhz.iotstarter.enums.GpioBuildEnum;
import com.xhz.iotstarter.enums.IotDeviceEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author: xuhongzhuo
 * @Date: 2022/2/10 1:00 PM
 *
 * Iot设配配置文件类
 */
@Component
@ConfigurationProperties("iot")
@Data
public class IotProperties {

    /**
     * gpio 端口的初始化方式
     */
    private GpioBuildEnum buildMethod;
    
    /**
     * Iot设备的gpio口和设备的映射  一般是 dht11 : 1 这种形式的
     */
    private Map<IotDeviceEnum,Integer> iotPinMap;

    /**
     * Iot设备的gpio口批量对应关系 例如: dht11: [1,2,3,4,5] 这种形式
     */
    private Map<IotDeviceEnum, List<Integer>> iotDevicePinsMap;

    /**
     * pcf8591 的数值，默认是0x48
     */
    private int pcf8591;

    /**
     * adm模块是否开启
     */
    private boolean ad;
}
