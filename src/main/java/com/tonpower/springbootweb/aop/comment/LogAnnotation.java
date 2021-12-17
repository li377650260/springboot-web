package com.tonpower.springbootweb.aop.comment;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

    String model() default "";

    String operator() default "";
}
