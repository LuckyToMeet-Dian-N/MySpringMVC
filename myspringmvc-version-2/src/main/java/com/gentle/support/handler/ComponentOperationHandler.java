package com.gentle.support.handler;

import com.gentle.annotation.Component;
import com.gentle.bean.BeanInfomation;

/**
 * @author Gentle
 * @date 2019/06/19 : 16:16
 */
public class ComponentOperationHandler implements AnnotationStrategy {

    @Override
    public BeanInfomation handlerBean(Class<?> clazz) {
        BeanInfomation beanInfomation = new BeanInfomation();
        Component annotation = clazz.getAnnotation(Component.class);
        if ("".equals(annotation.value())) {
            beanInfomation.setId(clazz.getName());
        } else {
            beanInfomation.setId(annotation.value());
        }
        beanInfomation.setClazz(clazz.getName());
        return beanInfomation;
    }
}
