package com.devpt.collapsar.config.audit.resolver;

import com.devpt.collapsar.config.audit.*;
import com.google.gson.Gson;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by chenyong on 2016/5/24.
 */
@Aspect
@Component
@SuppressWarnings("unchecked")
public class AuditLogResolver {
    private static final Logger logger = LoggerFactory.getLogger(AuditLogResolver.class);
    private static enum MethodSupport {SET,GET}

    @Pointcut(value = "@annotation(com.devpt.collapsar.config.audit.AuditLog)")
    private void auditLogPointcut(){}


    @Around("auditLogPointcut()")
    private Object writeAroundLog(ProceedingJoinPoint joinPoint) throws Throwable{
        Method method = ((MethodSignature)joinPoint.getSignature()).getMethod();
        if(method == null || !method.isAnnotationPresent(AuditLog.class)){
            return joinPoint.proceed();
        }

        final AuditLog auditLog = method.getAnnotation(AuditLog.class);
        AuditLogHelper auditLogHelper = AuditLogHelper.getLogger(auditLog.module());

        final AuditLogInfo auditLogInfo = new AuditLogInfo();
        auditLogInfo.setModule(auditLog.module());
        auditLogInfo.setMetric(auditLog.metric());

        this.writeBeforeLog(auditLogHelper, auditLogInfo, method.getParameters(), joinPoint.getArgs());
        final Object retVal = joinPoint.proceed();
        this.writeAfterLog(auditLogHelper, auditLogInfo, retVal);

        return retVal;
    }


    /**
     * write before audit log
     * @param auditLogHelper
     * @param auditLogInfo
     * @param parameters
     * @param args
     */
    private void writeBeforeLog(AuditLogHelper auditLogHelper, AuditLogInfo auditLogInfo, Parameter[] parameters, final Object[] args){
        try{
            if(parameters == null || args == null || parameters.length < 0 || parameters.length != args.length){
                logger.debug("writeBeforeLog: args/parameter is invlaid.");
                return;
            }

            final List annoArgs = new ArrayList<>();
            for(int i=0; i<parameters.length; i++){
                Parameter param = parameters[i];
                if(param == null){
                    logger.debug("writeBeforeLog: annotation args is null.(for in)");
                    continue;
                }
                if(param.isAnnotationPresent(AuditLogArg.class)){
                    annoArgs.add(args[i]);
                }
            }
            if(annoArgs.isEmpty()){
                logger.warn("writeBeforeLog: annotation args list is null.");
                return;
            }
            auditLogHelper.log(auditLogInfo.getMetric(), getLogs(annoArgs));

        }catch (Throwable t){
            logger.error("writeBeforeLog: catch exception, ex={}", t.getMessage());
        }
    }


    /**
     * write after audit log
     * @param auditLogHelper
     * @param auditLogInfo
     * @param args
     */
    private void writeAfterLog(AuditLogHelper auditLogHelper, AuditLogInfo auditLogInfo, final Object args){
        try{
            if(args == null){
                logger.debug("writeAfterLog: args is null.");
                return;
            }
            auditLogHelper.log(auditLogInfo.getMetric(), getLogs(Arrays.asList(args)));
        }catch(Throwable t){
            logger.error("writeAfterLog: catch exception, ex={}", t.getMessage());
        }
    }


    private static String getLogs(final List args) throws Exception{
        final List targetArgs = new ArrayList<>();
        for(final Object arg : args){
            Object targetArg;
            try {
                targetArg = arg.getClass().newInstance();
                BeanUtils.copyProperties(arg, targetArg);
            } catch (Exception e) {
                logger.warn("getLogs: catch exception, ex={}", e.getMessage());
                continue;
            }

            final Class cls = targetArg.getClass();
            Field[] fields = cls.getDeclaredFields();
            for(Field field : fields){
                if(!field.isAnnotationPresent(AuditLogFild.class)){
                    continue;
                }
                String fieldName = field.getName();

                Method getMethod = null;
                Method setMethod = null;
                try{
                    getMethod = getMethod(MethodSupport.GET, field, cls);
                    setMethod = getMethod(MethodSupport.SET, field, cls);
                }catch (Exception ex){
                    logger.error("getLogs: getMethod exception, fieldName={}, ex={}.", fieldName, ex.getMessage());
                    continue;
                }

                if(null == getMethod || null == setMethod){
                    logger.warn("getLogs: set or get Method is null. fieldName={}", fieldName);
                    continue;
                }

                getMethod.setAccessible(true);
                final String val = (String)getMethod.invoke(targetArg);
                if(null == val || val.isEmpty()){
                    logger.debug("getLogs: GetMethod value is null. fieldName={}", fieldName);
                    continue;
                }

                setMethod.setAccessible(true);
                MaskType maskType = field.getAnnotation(AuditLogFild.class).type();
                setMethod.invoke(targetArg, maskType.getMaskText(val));
            }

            targetArgs.add(targetArg);

        }
        return new Gson().toJson(targetArgs);
    }


    /**
     * get method
     * @param methodType
     * @param field
     * @param clazz
     * @return
     */
    private static Method getMethod(final MethodSupport methodType, Field field, Class clazz) throws NoSuchMethodException{
        String methodName = "";
        String fildName = field.getName();
        if(MethodSupport.GET == methodType){
            methodName = "get" + fildName.replaceFirst(fildName.substring(0,1), fildName.substring(0, 1).toUpperCase());
            return clazz.getDeclaredMethod(methodName);
        }else if (MethodSupport.SET == methodType){
            methodName = "set"+ fildName.replaceFirst(fildName.substring(0,1), fildName.substring(0, 1).toUpperCase());
            return clazz.getDeclaredMethod(methodName, field.getType());
        }else{
            logger.error("getMethod: method type not support. methodType={}", methodType);
            return null;
        }
    }


    protected class AuditLogInfo{
        private String module;
        private String metric;

        public AuditLogInfo() {
        }

        public AuditLogInfo(String module, String metric) {
            this.module = module;
            this.metric = metric;
        }

        public String getModule() {
            return module;
        }

        public void setModule(String module) {
            this.module = module;
        }

        public String getMetric() {
            return metric;
        }

        public void setMetric(String metric) {
            this.metric = metric;
        }
    }
}
