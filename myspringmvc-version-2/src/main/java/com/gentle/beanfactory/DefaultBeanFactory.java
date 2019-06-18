package com.gentle.beanfactory;

import com.gentle.bean.BeanInfomation;
import com.gentle.util.Assert;
import com.gentle.util.ClassUtils;
import com.gentle.util.TypeChoose;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Gentle
 * @date 2019/06/17 : 16:15
 */
public class DefaultBeanFactory extends AbstractBeanFactory {

    private Map<Class<?>, Object> beansClass = new ConcurrentHashMap<>(32);

    private Map<String, Object> beanName = new ConcurrentHashMap<>(32);

    private Map<String, Method> urlMapping = new ConcurrentHashMap<>(32);

    @Override
    public <T> T getBean(String name) {

        Assert.notNull(name);

        return (T) beanName.get(name);
    }

    @Override
    public <T> T getBean(Class<?> clazz) {
        Assert.notNull(clazz);
        return (T) beansClass.get(clazz);
    }

    @Override
    public <T> T getMapping(String url) {
        Assert.notNull(url);
        return (T) urlMapping.get(url);
    }

    @Override
    public boolean isSingleton(String name) {
        return false;
    }

    @Override
    public void registerBean(BeanInfomation beanInfomation) {
        Assert.notNull(beanInfomation);

        doRegisterBean(beanInfomation);
    }

    @Override
    public DefaultBeanFactory getBeanFactory() {

        return this;
    }

    /**
     * 注入bean
     *
     * @param beanInfomation bean信息对象
     */
    private void doRegisterBean(BeanInfomation beanInfomation) {
        String type = beanInfomation.getType();
        try {
            Class<?> classByClassName = ClassUtils.getClassByClassName(beanInfomation.getClazz());

            Object o = classByClassName.newInstance();

            Class<?>[] interfaces = classByClassName.getInterfaces();
            //注入 ioc 中
            beansClass.put(classByClassName, o);
            beanName.put(classByClassName.getName(), o);
            if (interfaces.length > 0) {
                Arrays.stream(interfaces).forEach(e -> {
                    beansClass.put(e, o);
                    beanName.put(e.getName(), o);
                });
            }
            //判断是否为 controller 类型
            if (type.equals(TypeChoose.Type.CONTROLLER)) {
                //校验对象中字段是否为空
                Assert.notNull(beanInfomation.getMethodName());
                Assert.notNull(beanInfomation.getUrl());

                Method[] methods = classByClassName.getMethods();
                AtomicBoolean isExist = new AtomicBoolean(false);
                Arrays.stream(methods).forEach(e -> {
                    if (e.getName().equals(beanInfomation.getMethodName())) {
                        isExist.set(true);
                        urlMapping.put(beanInfomation.getUrl(), e);
                    }
                });
                Assert.isTrue(isExist.get());
            }
            System.out.println(urlMapping);
            System.out.println(beansClass);
            System.out.println(beanName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
