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
     * 根据 url 获取 Method 对象
     * @param <T> Method
     * @return
     */
    <T> T getMapping(String url);

    /**
     * 是否为单例
     *
     * @param name
     * @return
     */
    boolean isSingleton(String name);



}
