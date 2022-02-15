package com.xhz.iotstarter;

import com.pi4j.wiringpi.Gpio;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 针对iot设备的starter，希望可以开箱即用的配置
 */
@SpringBootApplication
public class IotStarterApplication {


    public static void main(String[] args) {
        SpringApplication.run(IotStarterApplication.class, args);
    }

}
