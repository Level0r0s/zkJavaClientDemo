package com.xiaqi.dubbo.provider;

import com.xiaqi.dubbo.api.DemoService;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author xiaqi
 * @date 2019/7/14
 */
public class Provider {

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext applicationContext = new ClassPathXmlApplicationContext("provider.xml");
        System.in.read();
        applicationContext.close();
    }
}
