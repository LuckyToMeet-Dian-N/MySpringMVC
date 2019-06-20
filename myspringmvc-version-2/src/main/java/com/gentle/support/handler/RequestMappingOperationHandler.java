package com.gentle.support.handler;

import com.gentle.annotation.RequestMapping;
import com.gentle.bean.BeanInfomation;
import com.gentle.util.Assert;
import com.gentle.util.TypeChoose;
import java.lang.reflect.Method;

/**
 * @author Gentle
 * @date 2019/06/20 : 10:33
 */
public class RequestMappingOperationHandler implements AnnotationStrategy {
    @Override
    public BeanInfomation handlerBean(Class<?> clazz) {

        RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);

        BeanInfomation beanInfomation = new BeanInfomation();
        beanInfomation.setType(TypeChoose.Type.MAPPING);

        Assert.notNull(requestMapping.value());

        Method[] methods = clazz.getMethods();
        StringBuilder url = new StringBuilder("/" + requestMapping.value());
        for (Method method : methods) {
            if (method.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                url.append("/").append(annotation.value());
                beanInfomation.setMethodName(method.getName());
            }
        }
        beanInfomation.setUrl(url.toString());

        beanInfomation.setId(clazz.getName());
        beanInfomation.setClazz(clazz.getName());

        return beanInfomation;
    }


}

