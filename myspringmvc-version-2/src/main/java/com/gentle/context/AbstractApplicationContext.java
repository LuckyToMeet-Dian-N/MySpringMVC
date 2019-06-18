package com.gentle.context;


import com.gentle.bean.BeanInfomation;
import com.gentle.beanfactory.BeanFactory;
import com.gentle.beanfactory.DefaultBeanFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Gentle
 * @date 2019/06/17 : 16:18
 */

public abstract class AbstractApplicationContext implements ConfigApplicationContext {

    private Map<Class<?>, Object> beansClass = new ConcurrentHashMap<>();

    private Map<Class<?>, Object> mapping = new ConcurrentHashMap<>();

    private Map<Class<?>, Object> beanName = new ConcurrentHashMap<>();


    public abstract void registerBean(BeanInfomation beanInfomation);

    @Override
    public <T> T getBean(String name) {

        return getBeanFactory().getBean(name);
    }

    @Override
    public <T> T getBean(Class<?> clazz) {
        return getBeanFactory().getBean(clazz);
    }

    @Override
    public boolean isSingleton(String name) {
        return getBeanFactory().isSingleton(name);
    }

    @Override
    public void refresh() {

        try {
            BeanFactory beanFactory = getBeanFactory();



        } catch (Exception e) {

        }

    }





}
