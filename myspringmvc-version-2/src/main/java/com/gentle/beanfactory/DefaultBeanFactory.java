package com.gentle.beanfactory;

import com.gentle.bean.BeanInfomation;
import com.gentle.util.Assert;
import com.gentle.util.TypeChoose;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Gentle
 * @date 2019/06/17 : 16:15
 */
public class DefaultBeanFactory extends AbstractBeanFactory {

    private Map<Class<?>, Object> beansClass = new ConcurrentHashMap<>();

    private Map<Class<?>, Object> mapping = new ConcurrentHashMap<>();

    private Map<String, Object> beanName = new ConcurrentHashMap<>();

    @Override
    public <T> T getBean(String name) {

        Assert.notNull(name);

        return (T) beanName.get(name);
    }

    @Override
    public <T> T getBean(Class<?> clazz) {
        Assert.notNull(clazz);
        return (T) beansClass.get(clazz);
    }

    @Override
    public boolean isSingleton(String name) {
        return false;
    }

    @Override
    public void registerBean(BeanInfomation beanInfomation) {
        Assert.notNull(beanInfomation);

        String bean = TypeChoose.Type.BEAN;
        if (bean.equals(beanInfomation.getType())){

        }
    }

    @Override
    public BeanFactory getBeanFactory() {

        return this;
    }

    private void doRegisterBean(BeanInfomation beanInfomation){

    }



}
