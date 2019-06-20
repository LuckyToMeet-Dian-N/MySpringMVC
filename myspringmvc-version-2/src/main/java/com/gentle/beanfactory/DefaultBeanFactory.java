package com.gentle.beanfactory;

import com.gentle.annotation.Autowire;
import com.gentle.annotation.RequestMapping;
import com.gentle.bean.BeanInfomation;
import com.gentle.util.Assert;
import com.gentle.util.ClassUtils;
import com.gentle.util.TypeChoose;

import java.lang.reflect.Field;
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
    public void mappingRegister() {

        doMappingRegister();
    }

    private void doMappingRegister() {

        try {
            for (Map.Entry<Class<?>, Object> entry : beansClass.entrySet()) {
                Class<?> clazz = entry.getValue().getClass();
                //找到所有方法
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    StringBuilder url = new StringBuilder();

                    if (clazz.isAnnotationPresent(RequestMapping.class)) {
                        RequestMapping annotation = clazz.getAnnotation(RequestMapping.class);
                        url.append(annotation.value());
                    }
                    if (method.isAnnotationPresent(RequestMapping.class)) {
                        RequestMapping annotation1 = method.getAnnotation(RequestMapping.class);
                        url.append("/").append(annotation1.value());
                        Assert.isTrue(!urlMapping.containsKey(url.toString()));
                        urlMapping.put(url.toString(),method);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void autowireRegister() {
        try {
            for (Map.Entry<Class<?>, Object> entry : beansClass.entrySet()) {
                Class<?> aClass = entry.getValue().getClass();
                Field[] declaredFields = aClass.getDeclaredFields();
                for (Field field :declaredFields){
                    field.setAccessible(true);
                    if (field.isAnnotationPresent(Autowire.class)){
                        Autowire annotation = field.getAnnotation(Autowire.class);
                        String value = annotation.value();
                        if ("".equals(value)) {
                            Class<?> aClass1 = field.getType();
                            Object bean = getBean(aClass1);
                            setField(entry.getValue(), field, bean);
                        } else {
                            //不为空直接时根据输入的值返回
                            String name = annotation.value();
                            Object bean = getBean(name);
                            setField(entry.getValue(), field, bean);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void setField(Object bean1, Field field, Object bean)  {
        try {
            field.set(bean1, bean);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
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
            if (type.equals(TypeChoose.Type.BEAN)) {

                Object o = classByClassName.newInstance();

                Class<?>[] interfaces = classByClassName.getInterfaces();
                if (beansClass.containsKey(classByClassName) || beanName.containsKey(classByClassName.getName())) {
                    throw new IllegalArgumentException("bean  已经存在！");
                }
                //注入 ioc 中
                beansClass.put(classByClassName, o);
                beanName.put(classByClassName.getName(), o);
                if (interfaces.length > 0) {
                    Arrays.stream(interfaces).forEach(e -> {
                        beansClass.put(e, o);
                        beanName.put(e.getName(), o);
                    });
                }
            }
            //判断是否为 mapping 类型
            if (type.equals(TypeChoose.Type.MAPPING)) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

