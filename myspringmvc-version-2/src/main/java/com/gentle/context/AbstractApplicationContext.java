package com.gentle.context;


import com.gentle.bean.BeanInfomation;
import com.gentle.beanfactory.BeanFactory;
import com.gentle.beanfactory.DefaultBeanFactory;
import com.gentle.support.AnnotationLoad;
import com.gentle.util.Assert;
import com.gentle.util.TypeChoose;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Gentle
 * @date 2019/06/17 : 16:18
 */

public abstract class AbstractApplicationContext implements ConfigApplicationContext {

    /**
     * 注册bean到ioc中
     *
     * @param beanInfomation bean  信息
     */
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
    public <T> T getMapping(String url) {
        return getBeanFactory().getMapping(url);
    }

    /**
     * 核心载入类
     * @param beanInfomations bean 信息
     */
    @Override
    public void refresh(List<BeanInfomation> beanInfomations) {
        try {

            initIoc(beanInfomations);

            initAnnotation(beanInfomations);

        } catch (Exception e) {

        }
    }

    private void initIoc(List<BeanInfomation> beanInfomations) {
        beanInfomations.forEach(e-> getBeanFactory().registerBean(e));
    }


    /**
     * 注解驱动
     * @param beanInfomations bean 信息
     */
    private void initAnnotation(List<BeanInfomation> beanInfomations) {

        List<String> list = new ArrayList<>();
        beanInfomations.forEach(e->{
            if (e.getType().equals(TypeChoose.Type.PACKAGE)) {
                list.add(e.getPackages());
            }
        });
        AnnotationLoad annotationLoad = new AnnotationLoad(list);
        annotationLoad.setDefaultBeanFactory(getBeanFactory());
        //开启载入注解
        annotationLoad.annotationLoader();
    }


}
