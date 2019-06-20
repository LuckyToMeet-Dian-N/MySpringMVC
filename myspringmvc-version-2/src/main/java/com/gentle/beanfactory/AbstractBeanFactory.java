package com.gentle.beanfactory;

import com.gentle.bean.BeanInfomation;

/**
 * @author Gentle
 * @date 2019/06/17 : 21:15
 */
public abstract class AbstractBeanFactory implements  BeanFactory{
    /**
     * 注册 bean
     * @param beanInfomation bean 信息
     */
    public abstract void registerBean(BeanInfomation beanInfomation);
    /**
     * 请求 url 注入容器
     */
    public abstract void mappingRegister();

    /**
     * 注入，将@autowire 注解标注的字段注入bean
     */
    public abstract void autowireRegister();

    /**
     * 获取bean 工厂
     * @return
     */
    public abstract BeanFactory getBeanFactory();


}
