package com.gentle.controller;

import com.gentle.annotation.MyAutowire;
import com.gentle.annotation.MyController;
import com.gentle.annotation.MyRequestMapping;
import com.gentle.service.UserService;
import com.gentle.vo.Users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Gentle
 * @date 2019/04/06 : 22/59
 */
@MyController
@MyRequestMapping(value = "test")
public class UserController {

    @MyAutowire
    private UserService userService;


    @MyRequestMapping(value = "test")
    public String res( String userName,Users users, Integer gentle,HttpServletResponse response,HttpServletRequest request){
        System.out.println(request.getRequestURI());
        String aa = userService.aa();
        System.out.println("aa的值："+aa);
        System.out.println("UserInfo:"+users+"      userName:"+userName+"       gentle:"+gentle);
        System.out.println("哈哈哈哈哈哈哈哈哈哈哈哈，啊啊啊啊");

        try {
            PrintWriter writer = response.getWriter();
           writer.write("请求成功"+users);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Gentle";
    }


}
