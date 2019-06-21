package com.gentle.context;

import com.gentle.bean.BeanInfomation;
import com.gentle.beanfactory.DefaultBeanFactory;
import com.gentle.resources.DefaultXmlBeanReader;
import com.gentle.test.Test2Controller;
import com.gentle.test.TestController;

import java.io.InputStream;


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
//        BeanInfomation beanInfomation = new BeanInfomation();
//        beanInfomation.setClazz("com.gentle.util.ClassUtils");
//        beanInfomation.setId("com.gentle.util.ClassUtils");
//        defaultApplicationContext.registerBean(beanInfomation);
//        DefaultBeanFactory beanFactory = defaultApplicationContext.getBeanFactory();
//        System.out.println(beanFactory);
        defaultApplicationContext.loader("");
        DefaultBeanFactory beanFactory = defaultApplicationContext.getBeanFactory();
        TestController bean = beanFactory.getBean(TestController.class);

        Test2Controller bean1 = beanFactory.getBean(Test2Controller.class);
        bean1.aa();
    }


}
