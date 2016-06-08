package com.devpt.collapsar.config.access.resolver;

import com.devpt.collapsar.config.access.UserCheckHandler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
 * Created by chenyong on 2016/6/7.
 */
@Aspect
@Component
public class UserCheckResolver {
    private static final Logger logger = LoggerFactory.getLogger(UserCheckResolver.class);
    @Autowired
    private ReloadableResourceBundleMessageSource messageSource;
    @Autowired
    private UserCheckHandler userCheckHandler;



    @Pointcut(value = "@annotation(com.devpt.collapsar.config.access.UserCheck)")
    private void userCheckPct(){}

    /**
     * check user token
     * @return
     * @throws Throwable
     */
    @Around("userCheckPct()")
    private Object doCheckUser(ProceedingJoinPoint jp) throws Throwable{
        try{
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            // get parameters
            String userid = request.getParameter("u");
            String token = request.getParameter("t");
            String channel = request.getParameter("c");
            logger.debug("doCheckUser: start, uri={}, userid={}, token={}, channel={}", request.getServletPath(), userid, token, channel);
            // check args
            if (StringUtils.isEmpty(userid)) {
                logger.error("doCheckUser: userid is null.");
                doSendError();
                return null;
            }
            if (StringUtils.isEmpty(token)) {
                logger.error("doCheckUser: token is null.");
                doSendError();
                return null;
            }
            if (StringUtils.isEmpty(channel)) {
                logger.error("doCheckUser: channel is null.");
                doSendError();
                return null;
            }

            if (userCheckHandler.checkUser(Integer.valueOf(userid), token, Integer.valueOf(channel))) {
                return jp.proceed();
            } else {
                logger.error("doCheckUser: checkUser fail, userid={}, token={}, channel={}", userid, token, channel);
                doSendError();
                return null;
            }
        }catch(Exception ex){
            logger.error("doCheckUser: catch e.", ex);
            doSendError();
        }finally {
            logger.debug("doCheckUser end.");
        }
        return null;
    }

    private void doSendError() throws IOException {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, messageSource.getMessage("response.failure.token.invalid", null, Locale.SIMPLIFIED_CHINESE));
    }

}
