package com.gentle.test;

import com.gentle.annotation.Autowire;
import com.gentle.annotation.RequestMapping;
import com.gentle.annotation.RestController;

/**
 * @author Gentle
 * @date 2019/06/20 : 12:55
 */
@RestController
@RequestMapping(value = "abc")
public class TestController {

    @Autowire
    TestService testService;

    @RequestMapping(value = "bb")
    public String dd(String name,Integer age){
        System.out.println("名字："+name +"   "+"年龄："+age);
        System.out.println(testService);
        return "abcdddd";
    }



}
