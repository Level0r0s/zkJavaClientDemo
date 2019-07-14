package com.xiaqi.dubbo.provider;

import com.xiaqi.dubbo.api.DemoService;

/**
 * @author xiaqi
 * @date 2019/7/14
 */
public class DemoServiceImpl implements DemoService {

    @Override
    public String sayHello(String username) {
        return "Hello,"+username;
    }
}
