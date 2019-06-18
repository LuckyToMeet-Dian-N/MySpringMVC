package com.gentle.util;

import java.util.function.Supplier;

/**
 * @author Gentle
 * @date 2019/06/18 : 12:51
 */
public abstract class Assert {
    private   static  final String OBJECT_IS_NULL = " ]对象为空!";

    private static final String PRIFIX = "[ ";

    /**
     * 断言不为空
     * @param object 对象
     * @param message 信息
     */
    public static void notNull( Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言不为空
     * @param object 对象
     */
    public static void notNull( Object object) {
       notNull(object,PRIFIX+ object+OBJECT_IS_NULL);
    }

    /**
     *
     * @param expression  期望值
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     *
     * @param expression 期望值
     */
    public static void isTrue(boolean expression) {
        isTrue(expression, "[ "+expression+" ] 应该为 true");
    }


}
