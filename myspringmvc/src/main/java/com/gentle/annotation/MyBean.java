package com.gentle.annotation;

import java.lang.annotation.*;

/**
 * @author Gentle
 * @date 2019/04/06 : 20/23
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyBean {
    String value() default "";
}