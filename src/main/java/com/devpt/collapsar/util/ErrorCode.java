package com.devpt.collapsar.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.util.Assert;

import java.util.Locale;
import java.util.Objects;

/**
 * Created by lishaoyan on 2015/5/20.
 * <p>
 * Error Code Rules
 * <p>
 * - an integer (>=0)
 * - range:
 * 0  succes, no error
 * 1 - 999: system errors or general errors
 * 1000 - 1999: user's errors
 * 2000 - 2999: bankcard's errors
 * 3000 - 3999: transaction's errors
 */
public abstract class ErrorCode {

    private static final Logger logger = LoggerFactory.getLogger(ErrorCode.class);

    // error message prefix
    private static final String ERR_MESSAGE_FORMAT = "error.%d";

    // message bundle source
    private static AbstractMessageSource messageSource = null;

    // system error
    public static final int SYS_SUCCESS = 0;
    public static final int SYS_FAIL = 1;
    public static final int SYS_TIMEOUT = 2;
    public static final int SYS_TOKEN_INVALID = 3;
    public static final int SYS_CHANNEL_INVALID = 4;
    public static final int SYS_PARAM_INVALID = 5;



    /**
     * get the error message of the specified error code
     *
     * @param errorCode the error code
     * @param locale    the locale of error message
     * @return the error messsage
     */
    public static String getErrorMessage(int errorCode, Objects[] objects, Locale locale) {
        Assert.isTrue(errorCode >= 0, "error code must great than zero");

        if (messageSource == null) {
            logger.warn("can't get message because message bundle source is null");
            return String.format("ErrorCode = %d. Message Source isn't ready.", errorCode);
        }

        Locale lc = (locale == null) ? Locale.CHINA : locale;
        String message = messageSource.getMessage(String.format(ERR_MESSAGE_FORMAT, errorCode), objects, lc);

        if (message == null) {
            message = String.format("ErrorCode = %d. Message resource isn't ready.", errorCode);
        }

        return message;
    }

    /**
     * get the error message of the specified error code
     *
     * @param errorCode the error code
     * @return the error message with China Locale
     */
    public static String getErrorMessage(int errorCode) {
        return getErrorMessage(errorCode, null, null);
    }

    /**
     * decide if the message bundle source is set
     *
     * @return true if the message bunldes source is set, or false
     */
    public static boolean isMessageSourceReady() {
        return messageSource != null;
    }

    /**
     * set the message source bundle. it should be set before getMessage is invoked.
     *
     * @param messageSource the message bundle source
     */
    public static void setMessageSource(AbstractMessageSource messageSource) {
        ErrorCode.messageSource = messageSource;
    }

}
