package com.xhz.iotstarter.driver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: xuhongzhuo
 * @Date: 2022/5/15 2:59 PM
 * 敲击传感器
 */
@Component
@Slf4j
public class KickDriver extends BaseDriver{

    @Override
    public void initDevice() {
        // 初始化敲击传感器
    }
}
