package com.gentle.test;

import com.gentle.annotation.Autowire;

/**
 * @author Gentle
 * @date 2019/06/20 : 16:24
 */
public class Test2Controller {

    @Autowire
    private TestService testService;

    public void aa(){
        System.out.println(testService);
    }

}
