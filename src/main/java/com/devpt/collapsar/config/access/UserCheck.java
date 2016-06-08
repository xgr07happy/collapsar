package com.devpt.collapsar.config.access;

import java.lang.annotation.*;

/**
 * Created by chenyong on 2016/6/7.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface UserCheck {

    UTC[] value() default {UTC.U, UTC.T, UTC.C};

    enum UTC{
        U, T, C
    }
}
