package com.devpt.collapsar.config;

import com.devpt.collapsar.dao.LogRequestExceptionDao;
import com.devpt.collapsar.model.LogRequestException;
import com.devpt.collapsar.model.common.GenericResponse;
import com.devpt.collapsar.util.ErrorCode;
import com.devpt.collapsar.util.MyUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.ibatis.binding.MapperMethod;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.condition.RequestConditionHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Created by chenyong on 2016/6/13.
 */
@Aspect
@Component
public class RequestExceptionInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(RequestExceptionInterceptor.class);
    private static final String REQUEST_ENCODE = "UTF-8";
    @Autowired
    private LogRequestExceptionDao logRequestExceptionDao;

    @Pointcut(value = "@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    private void anyMethod(){}

    @Around("anyMethod()")
    public Object afterCompletion(ProceedingJoinPoint joinPoint) throws Throwable{
        int enterTime = MyUtils.getCurrentTimeStamp();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        request.setCharacterEncoding(REQUEST_ENCODE);
        String uri = request.getRequestURI();

        Object proceed = joinPoint.proceed();
        if(null != proceed){
            boolean insertLog = false;
            String errorCode = null;
            String message = null;

            if(proceed instanceof GenericResponse){
                GenericResponse genericResponse = (GenericResponse)proceed;
                if(ErrorCode.SYS_SUCCESS != genericResponse.getStatus()){
                    insertLog = true;
                    errorCode = genericResponse.getStatus()+"";
                    message = genericResponse.getMessage();
                }
            }



            if(insertLog){
                Object[] args = joinPoint.getArgs();
                Object body = null;
                Method method = ((MethodSignature)joinPoint.getSignature()).getMethod();
                Parameter[] parameters = method.getParameters();
                for(int i=0; i<parameters.length; i++){
                    if(null == parameters[i]){
                        continue;
                    }
                    if(parameters[i].isAnnotationPresent(RequestBody.class)){
                        body = args[i];
                        break;
                    }
                }

                String requestBody = null;
                if(null != body){
                    requestBody = new GsonBuilder().disableHtmlEscaping().create().toJson(body);
                }
                int currTime = MyUtils.getCurrentTimeStamp();
                LogRequestException log = new LogRequestException();
                log.setCreateTime(currTime);
                log.setUpdateTime(currTime);
                log.setRequestUrl(uri);
                log.setPostBody(requestBody);
                log.setEnterTime(enterTime);
                log.setErrorCode(errorCode);
                log.setMessage(message);
                this.logRequestExceptionDao.insertLogRequestException(log);
            }
        }
        return proceed;
    }

}
