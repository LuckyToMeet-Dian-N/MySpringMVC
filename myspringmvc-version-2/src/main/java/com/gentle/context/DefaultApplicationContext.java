package com.gentle.context;

import com.gentle.bean.BeanInfomation;
import com.gentle.beanfactory.BeanFactory;
import com.gentle.beanfactory.DefaultBeanFactory;

import java.io.InputStream;
import java.util.ArrayList;
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
        List<BeanInfomation> beanInfomations = reader.getBeanInfomations();
        beanInfomations.forEach(this::registerBean);
    }

    @Override
    public void loader(InputStream inputStream) {
        DefaultXmlBeanReader reader = new DefaultXmlBeanReader();
        reader.loader(inputStream);
        List<BeanInfomation> beanInfomations = reader.getBeanInfomations();
        beanInfomations.forEach(this::registerBean);
    }

}
