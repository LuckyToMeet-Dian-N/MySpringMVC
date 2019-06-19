package com.gentle.support;

import com.gentle.annotation.Component;
import com.gentle.annotation.Controller;
import com.gentle.annotation.RestController;
import com.gentle.annotation.Service;
import com.gentle.bean.BeanInfomation;
import com.gentle.support.handler.ComponentOperationHandler;
import com.gentle.support.handler.ControllerOperationHandler;
import com.gentle.support.handler.RestControllerOperationHandler;
import com.gentle.support.handler.ServiceOperationHandler;

/**
 * @author gentle
 */
public class AnnotationContext {


    public static BeanInfomation executeStrategy(Class clazz) {
        BeanInfomation beanInfomation=null;
        if (clazz.isAnnotationPresent(Component.class)){
            beanInfomation = new ComponentOperationHandler().handlerBean(clazz);
        }else if (clazz.isAnnotationPresent(Controller.class)){
            beanInfomation = new ControllerOperationHandler().handlerBean(clazz);
        }else if (clazz.isAnnotationPresent(Service.class)){
            beanInfomation = new ServiceOperationHandler().handlerBean(clazz);
        }else if (clazz.isAnnotationPresent(RestController.class)){
            beanInfomation = new RestControllerOperationHandler().handlerBean(clazz);
        }


        return beanInfomation;
    }
}