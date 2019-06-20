package com.gentle.support.handler;

import com.gentle.annotation.Controller;
import com.gentle.bean.BeanInfomation;

/**
 * @author Gentle
 * @date 2019/06/19 : 21:58
 */
public class ControllerOperationHandler implements AnnotationStrategy {
    @Override
    public BeanInfomation handlerBean(Class<?> clazz) {
        BeanInfomation beanInfomation = new BeanInfomation();
        Controller restController = clazz.getAnnotation(Controller.class);
        beanInfomation.setClazz(clazz.getName());
        if ("".equals(restController.value())) {
            beanInfomation.setId(clazz.getName());
        } else {
            beanInfomation.setId(restController.value());
        }
        return beanInfomation;
    }
}
