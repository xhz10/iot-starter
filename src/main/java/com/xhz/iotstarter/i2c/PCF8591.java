package com.xhz.iotstarter.i2c;

import com.pi4j.wiringpi.I2C;
import com.xhz.iotstarter.config.prop.IotProperties;
import com.xhz.iotstarter.enums.PCF8951IOEnum;
import com.xhz.iotstarter.exceptions.DeviceInitException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author: xuhongzhuo
 * @Date: 2022/3/12 4:42 PM
 */
@Component
@Slf4j
@Getter
@Setter
public class PCF8591 implements InitializingBean {

    @Autowired
    private IotProperties iotProperties;

    private int i2cAddress;

    /**
     * pcf8591文件描述符
     */
    private int fd;

    private boolean status;

    private void init() {
        if (iotProperties.getPcf8591() != 0) {
            setI2cAddress(iotProperties.getPcf8591());
            log.info("pcf8591 is : " + iotProperties.getPcf8591());
        } else {
            setI2cAddress(0x48);
            log.info("pcf8591 is : 0x48");
        }
    }

    public boolean pcf8591SetUp(int i2cAddress) {
        return (fd = I2C.wiringPiI2CSetup(i2cAddress)) < 0 ? false : true;
    }

    public int readInput(PCF8951IOEnum input) throws DeviceInitException {
        if (!isStatus()) {
            throw new DeviceInitException();
        }
        return I2C.wiringPiI2CReadReg8(fd, input.getNumber());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
        if (pcf8591SetUp(getI2cAddress())) {
            log.info("pcf 8591 初始化成功");
            setStatus(true);
        } else {
            log.info("pcf 8591 初始化失败");
            setStatus(false);
        }
    }
}
