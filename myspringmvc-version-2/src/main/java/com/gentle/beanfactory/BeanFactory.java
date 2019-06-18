package com.gentle.beanfactory;

/**
 * @author Gentle
 * @date 2019/06/17 : 10:38
 */
public interface BeanFactory {

    /**
     *
     * @param name
     * @param <T>
     * @return
     */
    <T> T getBean(String name);

    /**
     *
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T getBean(Class<?> clazz);

    /**
     *
     * @param name
     * @return
     */
    boolean isSingleton(String name);
}
