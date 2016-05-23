package com.devpt.collapsar.controller;

import com.devpt.collapsar.exception.CollapsarException;
import com.devpt.collapsar.model.common.GenericResponse;
import com.devpt.collapsar.model.rbac.QueryUsersByPageReq;
import com.devpt.collapsar.model.rbac.QueryUsersReq;
import com.devpt.collapsar.service.RbacService;
import com.devpt.collapsar.util.ErrorCode;
import com.devpt.collapsar.util.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by chenyong on 2016/5/17.
 */
@RestController
@RequestMapping("/rbac")
public class RbacController {
    private static final Logger logger = LoggerFactory.getLogger(RbacController.class);
    @Autowired
    private ReloadableResourceBundleMessageSource messageSource;
    @Autowired
    private RbacService rbacService;



    @RequestMapping(value = "/queryUsersByPage", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public GenericResponse queryUsersByPage(@RequestBody QueryUsersByPageReq queryUsersByPageReq){
        logger.info("queryUsersByPage: ====>queryUsersByPageReq={}", queryUsersByPageReq);

        GenericResponse response = null;
        if(null == queryUsersByPageReq){
            logger.error("queryUsersByPage: queryUsersByPageReq is null.");
            response = ResponseUtils.getErrorResp(ErrorCode.SYS_PARAM_INVALID, messageSource);
            return response;
        }

        try{
            response = ResponseUtils.getSuccessResp(rbacService.queryUsersByPage(queryUsersByPageReq), messageSource);
        }catch (CollapsarException cex){
            response = ResponseUtils.getErrorResp(cex.getErrorCode(), messageSource);
            logger.error("queryUsersByPage: catch exception, ex={}, resp={}", cex, response);
            return response;
        }catch (Exception ex){
            response = ResponseUtils.getErrorResp(ErrorCode.SYS_FAIL, messageSource);
            logger.error("queryUsersByPage: catch exception, ex={}, resp={}", ex, response);
        }

        logger.info("queryUsersByPage: <==== resp={}", response);
        return response;
    }




    @RequestMapping(value = "/queryUsers", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public GenericResponse queryUsers(@RequestBody QueryUsersReq queryUserReq){
        logger.info("queryUsers: ====>queryUserReq={}", queryUserReq);

        GenericResponse response = null;
        if(null == queryUserReq){
            logger.error("queryUsers: queryUserReq is null.");
            response = ResponseUtils.getErrorResp(ErrorCode.SYS_PARAM_INVALID, messageSource);
            return response;
        }

        try{
            response = ResponseUtils.getSuccessResp(rbacService.queryUsers(queryUserReq), messageSource);
        }catch (CollapsarException cex){
            response = ResponseUtils.getErrorResp(cex.getErrorCode(), messageSource);
            logger.error("queryUsers: catch exception, ex={}, resp={}", cex, response);
            return response;
        }catch (Exception ex){
            response = ResponseUtils.getErrorResp(ErrorCode.SYS_FAIL, messageSource);
            logger.error("queryUsers: catch exception, ex={}, resp={}", ex, response);
        }

        logger.info("queryUsers: <==== resp={}", response);
        return response;
    }

}
