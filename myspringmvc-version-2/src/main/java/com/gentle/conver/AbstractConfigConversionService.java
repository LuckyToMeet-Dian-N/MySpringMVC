package com.gentle.conver;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Gentle
 * @date 2019/06/21 : 12:35
 */
public abstract class AbstractConfigConversionService implements ConversionService{
    /**
     * 存储对象类型名字的map
     */
    private static Map<Class<?>, Class<?>> supportParameterfTypeMap = new ConcurrentHashMap<>();

    static {
        supportParameterfTypeMap.put(Integer.class, Integer.class);
        supportParameterfTypeMap.put(Double.class, Double.class);
        supportParameterfTypeMap.put(Long.class, Long.class);
        supportParameterfTypeMap.put(Byte.class, Byte.class);
        supportParameterfTypeMap.put(Short.class, Short.class);
        supportParameterfTypeMap.put(BigDecimal.class, BigDecimal.class);
        supportParameterfTypeMap.put(String.class,String.class);
    }

    /**
     * 判断是否为可支持的包装类型
     * @param clazz
     * @return
     */
    public boolean isSupportParameterType(Class<?> clazz){

        return supportParameterfTypeMap.containsKey(clazz);
    }




}
