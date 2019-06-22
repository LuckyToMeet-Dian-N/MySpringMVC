package com.gentle.web.method;

import com.gentle.beanfactory.BeanFactory;
import com.gentle.conversion.DefaultConversionService;
import com.gentle.parameter.LocalMethodParameterNameDiscoverer;
import com.gentle.parameter.ParameterNameDiscoverer;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Gentle
 * @date 2019/06/21 : 13:01
 */
public class MethodHandler {

    private Method method;

    private BeanFactory beanFactory;

    public MethodHandler(BeanFactory beanFactory, Method method) {
        this.method = method;
        this.beanFactory = beanFactory;
    }

    /**
     * 参数发现对象
     */
    private ParameterNameDiscoverer parameterNameDiscoverer = new LocalMethodParameterNameDiscoverer();

    /**
     * 参数绑定
     *
     * @param request 请求对象
     */
    public Object invokeForRequest(HttpServletRequest request) {
        Class<?> declaringClass = method.getDeclaringClass();
        Object bean = beanFactory.getBean(declaringClass);
        //获取方法 参数的名字和类型
        Map<String, Class<?>> methodArgumentNameAndType = getMethodArgumentNameAndType();
        //空方法，直接返回
        if (methodArgumentNameAndType == null || methodArgumentNameAndType.isEmpty()) {
            try {
                Object invoke = method.invoke(bean);
                return invoke;
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        //构造值对象
        Object[] objects = new Object[methodArgumentNameAndType.size()];
        //找到所有参数名
        Enumeration<String> parameterNames = request.getParameterNames();
        if (parameterNames == null) {
            throw new IllegalArgumentException("参数不能weik");
        }
        int i = 0;
        //迭代对比
        while (parameterNames.hasMoreElements()) {
            String s = parameterNames.nextElement();
            String parameter = request.getParameter(s);
            Class<?> aClass = methodArgumentNameAndType.get(s);
            if (aClass == null) {
                throw new IllegalArgumentException("方法不存在参数名[" + s + "],请检查后重试");
            }
            //类型转换
            objects[i] = new DefaultConversionService().convert(parameter, aClass);
            i++;
        }

        try {
            //方法注入
            Object invoke = method.invoke(bean, objects);
            return invoke;
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取方法参数类型和参数名
     *
     * @return
     */
    private Map<String, Class<?>> getMethodArgumentNameAndType() {
        //拿到所有参数类型
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length == 0) {
            return null;
        }
        //根据方法找到所有参数名
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);

        if (parameterNames==null){
            return null;
        }
        //将值与类型放入 map 中
        Map<String, Class<?>> map = new HashMap<>(parameterNames.length);
        for (int i = 0; i < parameterTypes.length; i++) {
            map.put(parameterNames[i], parameterTypes[i]);
        }
        return map;
    }


}
