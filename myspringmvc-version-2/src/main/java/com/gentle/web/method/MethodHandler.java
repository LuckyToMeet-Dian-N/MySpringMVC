package com.gentle.web.method;

import com.gentle.beanfactory.BeanFactory;
import com.gentle.conver.DefaultConversionService;
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

    private ParameterNameDiscoverer parameterNameDiscoverer = new LocalMethodParameterNameDiscoverer();

    /**
     * 参数绑定
     * @param request
     */
    public void invokeForRequest(HttpServletRequest request) {

        Map<String, Class<?>> methodArgumentNameAndType = getMethodArgumentNameAndType();
        if (methodArgumentNameAndType == null || methodArgumentNameAndType.isEmpty()) {
            return;
        }
        Object[] objects = new Object[methodArgumentNameAndType.size()];
        Enumeration<String> parameterNames = request.getParameterNames();
        if (parameterNames == null) {
            throw new IllegalArgumentException("参数不能weik");
        }
        int i = 0;
        while (parameterNames.hasMoreElements()) {
            String s = parameterNames.nextElement();
            String parameter = request.getParameter(s);
            Class<?> aClass = methodArgumentNameAndType.get(parameter);
            if (aClass == null) {
                throw new IllegalArgumentException("方法不存在参数名["+ parameter +"],请检查后重试");
            }
            //是否为基本类型
            objects[i] = new DefaultConversionService().convert(parameter, aClass);
            i++;
        }
        Class<?> declaringClass = method.getDeclaringClass();
        Object bean = beanFactory.getBean(declaringClass);
        try {
            method.invoke(bean, objects);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     *  获取方法参数类型和参数名
     * @return
     */
    private Map<String, Class<?>> getMethodArgumentNameAndType() {
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length == 0) {
            return null;
        }
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
        Map<String, Class<?>> map = new HashMap<>(parameterNames.length);
        for (int i = 0; i < parameterTypes.length; i++) {
            map.put(parameterNames[i], parameterTypes[i]);
        }
        return map;
    }


}
