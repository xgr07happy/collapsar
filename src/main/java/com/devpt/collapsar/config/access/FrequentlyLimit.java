package com.devpt.collapsar.config.access;

import java.lang.annotation.*;

/**
 * Created by chenyong on 2016/6/15.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FrequentlyLimit {
    int intervals() default 10;
}
