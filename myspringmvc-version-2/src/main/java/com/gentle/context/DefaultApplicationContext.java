package com.gentle.context;

import com.gentle.bean.BeanInfomation;
import com.gentle.beanfactory.DefaultBeanFactory;
import com.gentle.test.Test2Controller;
import com.gentle.test.TestController;
import com.gentle.util.TypeChoose;

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
//        DefaultXmlBeanReader reader = new DefaultXmlBeanReader();
//        reader.loader(location);
//        refresh(reader.getBeanInfomations());

        BeanInfomation beanInfomation = new BeanInfomation();
        beanInfomation.setClazz("com.gentle.util.ClassUtils");
        beanInfomation.setId("com.gentle.util.ClassUtils");
        BeanInfomation beanInfomation1 = new BeanInfomation();

        beanInfomation1.setPackages("com.gentle.test");
        beanInfomation1.setType(TypeChoose.Type.PACKAGE);

        BeanInfomation beanInfomation2 = new BeanInfomation();

        beanInfomation2.setMethodName("aa");
        beanInfomation2.setType(TypeChoose.Type.MAPPING);
        beanInfomation2.setUrl("url/logon");
        beanInfomation2.setId("com.gentle.test.Test2Controller");
        beanInfomation2.setClazz("com.gentle.test.Test2Controller");

        List<BeanInfomation> list = new ArrayList<>();
        list.add(beanInfomation);
        list.add(beanInfomation1);
        list.add(beanInfomation2);
        refresh(list);
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
        bean.dd();

        Test2Controller bean1 = beanFactory.getBean(Test2Controller.class);
        bean1.aa();
    }


}
