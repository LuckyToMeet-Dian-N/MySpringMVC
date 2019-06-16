package com.gentle.annotation;

import java.lang.annotation.*;

/**
 * @author Gentle
 * @date 2019/04/06 : 20/21
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyController {

    String value() default "";

}
