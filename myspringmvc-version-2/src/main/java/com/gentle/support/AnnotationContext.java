package com.gentle.support;

import com.gentle.annotation.*;
import com.gentle.bean.BeanInfomation;
import com.gentle.support.handler.*;

/**
 * @author gentle
 */
public class AnnotationContext {

    /**
     * 根据不同注解判断构建 bean 信息对象
     * @param clazz
     * @return
     */
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
        }else if (clazz.isAnnotationPresent(RequestMapping.class)){
                new RequestMappingOperationHandler().handlerBean(clazz);
        }else {
            beanInfomation = new BeanOperationHandler().handlerBean(clazz);
        }
        return beanInfomation;
    }
}