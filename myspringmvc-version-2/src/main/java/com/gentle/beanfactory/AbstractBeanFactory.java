package com.gentle.beanfactory;

import com.gentle.bean.BeanInfomation;

/**
 * @author Gentle
 * @date 2019/06/17 : 21:15
 */
public abstract class AbstractBeanFactory implements  BeanFactory{


    public abstract void registerBean(BeanInfomation beanInfomation);


    public abstract BeanFactory getBeanFactory();


}
