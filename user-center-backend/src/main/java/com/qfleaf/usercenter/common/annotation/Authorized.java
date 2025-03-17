package com.qfleaf.usercenter.common.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Authorized {
    @AliasFor("authority")
    int[] value() default {1};
    @AliasFor("value")
    int[] authority() default {1};
}
