package com.xiaqi.dubbo.consumer;

import com.xiaqi.dubbo.api.DemoService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author xiaqi
 * @date 2019/7/14
 */
public class Consumer {

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext applicationContext = new ClassPathXmlApplicationContext("consumer.xml");
        DemoService service = applicationContext.getBean(DemoService.class);
        System.out.println(service.sayHello("夏齐"));
        applicationContext.close();
        System.in.read();
    }
}
