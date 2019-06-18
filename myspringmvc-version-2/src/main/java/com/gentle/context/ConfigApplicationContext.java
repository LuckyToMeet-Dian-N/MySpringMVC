package com.gentle.context;

import com.gentle.beanfactory.BeanFactory;
import com.gentle.beanfactory.DefaultBeanFactory;

/**
 * @author Gentle
 * @date 2019/06/17:10:39
 */
public interface ConfigApplicationContext extends ApplicationContext {
    /**
     *
     */
    void refresh();


    DefaultBeanFactory getBeanFactory();

}
