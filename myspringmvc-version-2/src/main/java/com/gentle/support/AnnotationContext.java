package com.gentle.support;

import com.gentle.annotation.Component;
import com.gentle.annotation.Controller;
import com.gentle.annotation.RestController;
import com.gentle.annotation.Service;
import com.gentle.bean.BeanInfomation;
import com.gentle.support.handler.ComponentOperationHandler;

/**
 * @author gentle
 */
public class AnnotationContext {


    public static BeanInfomation executeStrategy(Class clazz) {
        BeanInfomation beanInfomation;
        if (clazz.isAnnotationPresent(Component.class)){
            beanInfomation = new ComponentOperationHandler().handlerBean(clazz);
        }else if (clazz.isAnnotationPresent(RestController.class)){

        }else if (clazz.isAnnotationPresent(Controller.class)){

        }else if (clazz.isAnnotationPresent(Service.class)){

        }else if (clazz.isAnnotationPresent(RestController.class)){

        }





        return null;
    }
}