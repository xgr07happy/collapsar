package com.devpt.collapsar.util;

import com.devpt.collapsar.model.common.GenericResponse;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * Created by chenyong on 2016/5/17.
 */
public class ResponseUtils {
    private static Map<Integer, String> ERROR_CODE_MAP = new HashMap<>();
    static{
        ERROR_CODE_MAP.put(ErrorCode.SYS_SUCCESS, "response.success");
        ERROR_CODE_MAP.put(ErrorCode.SYS_FAIL, "response.failure");
        ERROR_CODE_MAP.put(ErrorCode.SYS_TIMEOUT, "response.failure.timeout");
        ERROR_CODE_MAP.put(ErrorCode.SYS_TOKEN_INVALID, "response.failure.token.invalid");
        ERROR_CODE_MAP.put(ErrorCode.SYS_CHANNEL_INVALID, "response.failure.channel.invalid");
        ERROR_CODE_MAP.put(ErrorCode.SYS_PARAM_INVALID, "response.failure.param.invalid");
        ERROR_CODE_MAP.put(ErrorCode.SYS_OPRATE_TOO_FREQUENENTLY, "response.failure.too.frequently");

    }

    public static GenericResponse getSuccessResp(ReloadableResourceBundleMessageSource messageSource){
        return getSuccessResp(null, messageSource);
    }

    public static GenericResponse getSuccessResp(Object content, ReloadableResourceBundleMessageSource messageSource){
        GenericResponse response = new GenericResponse();
        response.setStatus(ErrorCode.SYS_SUCCESS);
        String code = ERROR_CODE_MAP.get(ErrorCode.SYS_SUCCESS);
        if(StringUtils.isEmpty(code)){
            throw new RuntimeException("error code is invalid.");
        }
        response.setMessage(messageSource.getMessage(code, null, Locale.SIMPLIFIED_CHINESE));
        response.setContent(content);
        return response;
    }

    public static GenericResponse getErrorResp(int status, ReloadableResourceBundleMessageSource messageSource){
        return getErrorResp(status, null, messageSource);
    }

    public static GenericResponse getErrorResp(int status, Objects[] args, ReloadableResourceBundleMessageSource messageSource){
        GenericResponse response = new GenericResponse();
        response.setStatus(status);
        String code = ERROR_CODE_MAP.get(status);
        if(StringUtils.isEmpty(code)){
            throw new RuntimeException("error code is invalid.");
        }
        response.setMessage(messageSource.getMessage(code, null, Locale.SIMPLIFIED_CHINESE));
        return response;
    }

}
