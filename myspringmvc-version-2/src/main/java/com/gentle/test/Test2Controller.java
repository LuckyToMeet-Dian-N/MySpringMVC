package com.gentle.test;

import com.gentle.annotation.Autowire;

/**
 * @author Gentle
 * @date 2019/06/20 : 16:24
 */
public class Test2Controller {

    @Autowire
    private TestService testService;

    public String aa(){

        return "这是 xml 方式注入容器的！";
    }

}
