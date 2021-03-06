package com.gentle.context;


import com.gentle.bean.BeanInfomation;
import com.gentle.support.AnnotationLoad;
import com.gentle.util.TypeChoose;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
            //ioc 容器初始化
            initIoc(beanInfomations);
            //注解初始化
            initAnnotation(beanInfomations);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * bean 信息
     * @param beanInfomations bean 信息
     */
    private void initIoc(List<BeanInfomation> beanInfomations) {
        //排除 BeanInfomation 中的包类型
        List<BeanInfomation> collect = beanInfomations.stream().filter(e -> !e.getType().equals(TypeChoose.Type.PACKAGE)).collect(Collectors.toList());
        //注册到容器中
        collect.forEach(e-> getBeanFactory().registerBean(e));
    }


    /**
     * 注解驱动
     * @param beanInfomations bean 信息
     */
    private void initAnnotation(List<BeanInfomation> beanInfomations) {
        //找到所有类型是包类型的，用于扫描注解，进行注入
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
