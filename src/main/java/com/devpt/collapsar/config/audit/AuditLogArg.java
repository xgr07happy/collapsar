package com.devpt.collapsar.config.audit;

import java.lang.annotation.*;

/**
 * Created by chenyong on 2016/5/25.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuditLogArg {
}
