package com.gentle.util;

/**
 * @author Gentle
 * @date 2019/06/18 : 13:10
 */
public class TypeChoose {

    public interface Type {
        /**
         * bean 的默认类型
         * 注册到ioc 容器
         */
        String BEAN = "bean";
        /**
         * properties 类型
         *
         */
        String PROPERTIES = "properties";
        /**
         * mapping 类型
         * 用于 url 映射
         */
        String MAPPING ="mapping";
        /**
         * mapping 包类型
         * 用于包扫描
         */
        String PACKAGE = "package";

    }


}
