package com.devpt.collapsar.config.access.resolver;

import com.devpt.collapsar.config.access.FrequentlyLimit;
import com.devpt.collapsar.util.MyUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by chenyong on 2016/6/15.
 */
@Aspect
@Component
public class FrequentlyLimitResolver {
    private static final Logger logger = LoggerFactory.getLogger(FrequentlyLimitResolver.class);
    private static final String REQUEST_ENCODE = "UTF-8";
    private static final String REDIS_REQUEST_FREQUENTLY_LIMIT_PREFIX = "request_frequently_limit_";
    @Autowired
    private ReloadableResourceBundleMessageSource messageSource;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;




    @Pointcut(value = "com.devpt.collapsar.config.access.FrequentlyLimit")
    private void anyMethod(){};






    @Around("anyMethod()")
    private Object doFrequentlyLimit(ProceedingJoinPoint joinPoint) throws Throwable{

        Method method = ((MethodSignature)joinPoint.getSignature()).getMethod();
        if(method == null || !method.isAnnotationPresent(FrequentlyLimit.class)){
            return joinPoint.proceed();
        }

        FrequentlyLimit frequentlyLimit = method.getAnnotation(FrequentlyLimit.class);
        int intervals = frequentlyLimit.intervals();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        request.setCharacterEncoding(REQUEST_ENCODE);
        String uri = request.getRequestURI();
        String userid = request.getParameter("u");

        if(!this.determineVisitByUserid(userid,uri, intervals)){
            this.doSendError();
            return null;
        }
        return joinPoint.proceed();

    }


    private void doSendError() throws IOException {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, messageSource.getMessage("response.failure.too.frequently", null, Locale.SIMPLIFIED_CHINESE));
    }



    private boolean determineVisitByUserid(String userid, String uri, int timeout) {
        if (StringUtils.isEmpty(userid) || StringUtils.isEmpty(uri) || timeout <= 0) {
            logger.debug("determineVisitByUserid: userid or uri or timeout is empty.");
            return true;
        }
        String keyName = REDIS_REQUEST_FREQUENTLY_LIMIT_PREFIX + userid + "_" + uri;

        if (this.stringRedisTemplate.opsForValue().get(keyName) != null) {
            logger.debug("determineVisitByUserid: access too frequently, keyName: {}", keyName);
            return false;
        }
        try {
            if (this.stringRedisTemplate.opsForValue().setIfAbsent(keyName, "")) {
                this.stringRedisTemplate.expire(keyName, timeout, TimeUnit.SECONDS);
                logger.debug("determineVisitByUserid: setkey keyName: {} ", keyName);
                return true;
            } else {
                logger.debug("determineVisitByUserid: Failed to create redis key '{}' because it exists.", keyName);
                return false;
            }
        } catch (Exception e) {
            logger.error("determineVisitByUserid: exception in creating redis key: {}", e);
            return false;
        }
    }

}
