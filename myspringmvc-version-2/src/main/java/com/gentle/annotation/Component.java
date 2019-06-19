package com.gentle.annotation;

import java.lang.annotation.*;

/**
 * @author Gentle
 * @date 2019/06/16 : 16:39
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {
    /**
     * 指定名字
     *
     * @return
     */
    String value() default "";

    /**
     * 版本号
     *
     * @return
     */
    String version() default "1.0";

    String type() default "bean";

    String methodName() default "";

    String packages() default "";

    String url() default "";
}
