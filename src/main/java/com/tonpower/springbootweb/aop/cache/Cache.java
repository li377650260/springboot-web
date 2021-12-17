package com.tonpower.springbootweb.aop.cache;

import java.lang.annotation.*;

/**
 * @description:
 * @author: li377650260
 * @date: 2021/10/29 22:39
 */

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {

    long expire() default 1 * 60 * 1000;
    String name() default "";
}
