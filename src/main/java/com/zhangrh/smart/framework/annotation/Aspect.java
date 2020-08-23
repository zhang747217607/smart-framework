package com.zhangrh.smart.framework.annotation;


import java.lang.annotation.*;

/**
 * 切面注解
 *
 * @version: 1.0
 * @author: zhangrenhua
 * @date: 2020/8/9 17:24
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    /**
     * 注解
     */
    Class<? extends Annotation> value();
}
