package com.gentle.context;

import com.gentle.bean.BeanInfomation;
import com.gentle.beanfactory.BeanFactory;
import com.gentle.beanfactory.DefaultBeanFactory;

import java.util.List;

/**
 * @author Gentle
 * @date 2019/06/17:10:39
 */
public interface ConfigApplicationContext extends ApplicationContext {
    /**
     *
     */
    void refresh(List<BeanInfomation> beanInfomations);


    DefaultBeanFactory getBeanFactory();

}
