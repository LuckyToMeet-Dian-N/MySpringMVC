package com.gentle.web;

import com.gentle.beanfactory.BeanFactory;
import com.gentle.beanfactory.DefaultBeanFactory;
import com.gentle.context.DefaultApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Gentle
 * @date 2019/06/18 : 14:12
 */
public  class DispatcherServlet  extends HttpServlet {

    private Map<String,Object> urlMapping = new ConcurrentHashMap<>();


    private DefaultBeanFactory beanFactory;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        super.doPost(req, resp);
    }

    @Override
    public void init(ServletConfig servletConfig)   {

        String contextConfigLocation = servletConfig.getInitParameter("contextConfigLocation");

        DefaultApplicationContext applicationContext = new DefaultApplicationContext();

        applicationContext.loader(contextConfigLocation);
        
        this.beanFactory =applicationContext.getBeanFactory();

    }










}
