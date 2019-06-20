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
     * 获取bean 工厂
     * @return
     */
    public abstract BeanFactory getBeanFactory();


}
