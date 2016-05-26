package com.devpt.collapsar.config.audit;

import java.lang.annotation.*;

/**
 * Created by chenyong on 2016/5/24.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface AuditLog {
    /**
     * module name
     * @return
     */
    String module();

    /**
     * metric name
     * @return
     */
    String metric();
}
