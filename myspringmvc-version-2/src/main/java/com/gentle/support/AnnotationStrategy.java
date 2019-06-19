package com.gentle.support;

import com.gentle.bean.BeanInfomation;

/**
 * 注解类策略接口
 * @author Gentle
 * @date 2019/06/19 : 16:10
 */
public interface AnnotationStrategy {
    /**
     *
     * @param clazz
     * @return
     */
    BeanInfomation handlerBean(Class<?> clazz);


}
