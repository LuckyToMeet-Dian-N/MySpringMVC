package com.gentle.support.handler;


import com.gentle.annotation.Service;
import com.gentle.bean.BeanInfomation;


/**
 * @author Gentle
 * @date 2019/06/19 : 21:58
 */
public class ServiceOperationHandler implements AnnotationStrategy {
    @Override
    public BeanInfomation handlerBean(Class<?> clazz) {
        BeanInfomation beanInfomation = new BeanInfomation();
        Service restController = clazz.getAnnotation(Service.class);
        beanInfomation.setClazz(clazz.getName());
        if ("".equals(restController.value())) {
            beanInfomation.setId(clazz.getName());
        } else {
            beanInfomation.setId(restController.value());
        }
        return beanInfomation;
    }
}
