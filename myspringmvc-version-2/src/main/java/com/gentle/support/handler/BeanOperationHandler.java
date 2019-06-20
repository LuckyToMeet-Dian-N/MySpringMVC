package com.gentle.support.handler;

import com.gentle.annotation.Bean;
import com.gentle.annotation.Component;
import com.gentle.bean.BeanInfomation;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author Gentle
 * @date 2019/06/19 : 22:00
 */
public class BeanOperationHandler implements AnnotationStrategy {
    @Override
    public BeanInfomation handlerBean(Class<?> clazz) {
        BeanInfomation beanInfomation = new BeanInfomation();
        if (clazz.isAnnotationPresent(Component.class)){
            Method[] methods = clazz.getMethods();
            //循环找到 bean 注解标注的方法，构建bean
            Arrays.stream(methods).forEach(method->{
                if (method.isAnnotationPresent(Bean.class)){
                    Bean annotation = method.getAnnotation(Bean.class);
                    Class<?> returnType = method.getReturnType();
                    if (!returnType.isInterface()){
                        beanInfomation.setClazz(returnType.getName());
                    }
                    if ("".equals(annotation.value())){
                        beanInfomation.setId(returnType.getName());
                    }else {
                        beanInfomation.setId(annotation.value());
                    }
                }
            });

        }
        return beanInfomation;
    }
}
