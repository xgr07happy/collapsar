package com.devpt.collapsar.config.audit;

/**
 * Created by chenyong on 2016/5/25.
 */
public interface AuditLogConfigurer {
    enum RbacControllerApi{
        ;
        public static final String MODULE_NAME = "RbacController";

        public static final String queryUsersByPage = "queryUsersByPage";
    }
}
