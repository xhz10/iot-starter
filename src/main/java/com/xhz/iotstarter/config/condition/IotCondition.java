package com.xhz.iotstarter.config.condition;


import com.pi4j.wiringpi.Gpio;
import com.xhz.iotstarter.enums.GpioBuildEnum;
import com.xhz.iotstarter.exceptions.GpioBuildException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.ObjectUtils;

/**
 * @Author: xuhongzhuo
 * @Date: 2022/2/12 12:59 PM
 */
@Slf4j
public class IotCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        //1、能获取到ioc使用的beanfactory
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        //2、获取类加载器
        ClassLoader classLoader = context.getClassLoader();
        //3、获取当前环境信息
        Environment environment = context.getEnvironment();
        GpioBuildEnum method = environment.getProperty("iot.build-method", GpioBuildEnum.class);
        if (ObjectUtils.isEmpty(method)) {
            return false;
        }
        System.out.println(method);
        int flag = -1;
        switch (method) {
            case WIRINGPISETUP:
                //flag = 2;
                //break;
                log.info("WIRINGPISETUP 初始化了");
            flag = Gpio.wiringPiSetup();break;
            case BCMSETUP:
                //flag = -1;
                //break;
            flag = Gpio.wiringPiSetupGpio();break;
            case PHYSETUP:
                //flag = -1;
                //break;
            flag = Gpio.wiringPiSetupPhys();break;
            case SYSSETUP:
                //flag = -1;
                //break;
            flag = Gpio.wiringPiSetupSys();break;
        }
        if (flag == -1) {
            log.error("初始化method有问题", new GpioBuildException());
            return false;
        }
        System.out.println("初始化了" + method);
        return true;
    }
}
