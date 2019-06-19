package com.gentle.annotation;

import java.lang.annotation.*;

/**
 * @author Gentle
 * @date 2019/06/16 : 16:41
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Bean {
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
