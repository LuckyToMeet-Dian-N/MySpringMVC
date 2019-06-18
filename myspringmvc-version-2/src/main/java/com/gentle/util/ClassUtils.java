package com.gentle.util;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Gentle
 * @date 2019/06/16 : 11:39
 */
public class ClassUtils {
    private static final String CLASS_FILE_SUFFIX = ".class";
    private static final String PACKAGE_SEPARATOR = ".";
    /**
     * 存储对象类型的map
     */
    private static Map<Class,Class<?>> supportParametersMap =new ConcurrentHashMap<>();
    /**
     * 存储对象类型名字的map
     */
    private static  Map<String,Class<?>> parameterfTypeNameMap = new ConcurrentHashMap<>();

    static {
        supportParametersMap.put(Integer.class,int.class);
        supportParametersMap.put(Double.class,double.class);
        supportParametersMap.put(Long.class,long.class);
        supportParametersMap.put(Byte.class,byte.class);
        supportParametersMap.put(Short.class,short.class);
        supportParametersMap.put(BigDecimal.class,BigDecimal.class);

        supportParametersMap.forEach((key,value)->{
            parameterfTypeNameMap.put(key.getName(),value);
        });
    }


    /**
     * 判断是否为支持的类型
     * @param typeName 类型的名字
     * @return 是否支持
     */
    public static boolean isSupportDataTypeByTypeName(String typeName){
        Assert.notNull(typeName);
        return parameterfTypeNameMap.containsKey(typeName);
    }

    /**
     *  类对象
     * @param clazzName 包名+类名
     * @return
     */
    public static Class<?> getClassByClassName(String clazzName){
        Assert.notNull(clazzName);
        try {
            return Class.forName(clazzName);
        } catch (ClassNotFoundException e) {
           e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断是否为支持的类型
     * @param clazz class对象
     * @return 是否支持
     */
    public static boolean isSupportDataType(Class<?> clazz){
        Assert.notNull(clazz);
        return supportParametersMap.containsKey(clazz);
    }

    /**
     * @param clazz class对象
     * @return
     */
    public static String getClassFileName(Class<?> clazz) {
        String className = clazz.getName();
        int lastDotIndex = className.lastIndexOf(PACKAGE_SEPARATOR);
        return className.substring(lastDotIndex + 1) + CLASS_FILE_SUFFIX;
    }

    public static void main(String[] args) {
        System.out.println(isSupportDataType(Integer.class));

        System.out.println(getClassFileName(Integer.class));
        System.out.println(isSupportDataTypeByTypeName(Integer.class.getName()));
    }







}
