package com.gentle.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Service {
    /**
     *  指定名字
     * @return
     */
    String value() default "";

    /**
     * 版本号
     * @return
     */
    String version() default "1.0";
}