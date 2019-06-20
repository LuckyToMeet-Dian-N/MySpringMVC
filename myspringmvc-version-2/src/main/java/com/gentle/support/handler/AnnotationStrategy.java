package com.gentle.support.handler;

import com.gentle.bean.BeanInfomation;

/**
 * 注解类策略接口
 * @author Gentle
 * @date 2019/06/19 : 16:10
 */
public interface AnnotationStrategy {
    /**
     * 构建bean 信息
     * @param clazz class 对象
     * @return
     */
    BeanInfomation handlerBean(Class<?> clazz);


}
