package com.gentle.parameter;

import java.lang.reflect.Method;

/**
 * @author Gentle
 * @date 2019/06/16 : 14:27
 */
public interface ParameterNameDiscoverer {

    /**
     * 根据方法获取参数名
     * @param method 方法
     * @return
     */
    String[] getParameterNames(Method method);


}
