package com.gentle.support.handler;

import com.gentle.annotation.Component;
import com.gentle.annotation.RestController;
import com.gentle.bean.BeanInfomation;

/**
 * @author Gentle
 * @date 2019/06/19 : 21:57
 */
public class RestControllerOperationHandler implements AnnotationStrategy {


    @Override
    public BeanInfomation handlerBean(Class<?> clazz) {
        BeanInfomation beanInfomation = new BeanInfomation();

        RestController restController = clazz.getAnnotation(RestController.class);
        beanInfomation.setClazz(clazz.getName());
        if ("".equals(restController.value())) {
            beanInfomation.setId(clazz.getName());
        } else {
            beanInfomation.setId(restController.value());
        }
        return beanInfomation;
    }
}
