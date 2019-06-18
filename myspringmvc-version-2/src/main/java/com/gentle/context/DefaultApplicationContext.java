package com.gentle.context;

import com.gentle.bean.BeanInfomation;
import com.gentle.beanfactory.DefaultBeanFactory;

import java.io.InputStream;
import java.util.List;

/**
 * @author Gentle
 * @date 2019/06/18 : 14:24
 */
public class DefaultApplicationContext extends AbstractConfigApplicationContext {

    @Override
    public void registerBean(BeanInfomation beanInfomation) {
        DefaultBeanFactory beanFactory = getBeanFactory();
        beanFactory.registerBean(beanInfomation);
    }

    @Override
    public void loader(String location) {
        DefaultXmlBeanReader reader = new DefaultXmlBeanReader();
        reader.loader(location);
        refresh(reader.getBeanInfomations());
    }

    @Override
    public void loader(InputStream inputStream) {
        //TODO
    }

    public static void main(String[] args) {
        DefaultApplicationContext defaultApplicationContext = new DefaultApplicationContext();
        BeanInfomation beanInfomation = new BeanInfomation();
        beanInfomation.setClazz("com.gentle.util.ClassUtils");
        beanInfomation.setId("12313");
        defaultApplicationContext.registerBean(beanInfomation);
        DefaultBeanFactory beanFactory = defaultApplicationContext.getBeanFactory();
        System.out.println(beanFactory);

    }


}
