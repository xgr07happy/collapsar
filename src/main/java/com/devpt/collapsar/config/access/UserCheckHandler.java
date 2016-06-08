package com.devpt.collapsar.config.access;

import com.devpt.collapsar.exception.CollapsarException;
import com.devpt.collapsar.model.ConfigOuterChannel;
import com.devpt.collapsar.service.ConfigService;
import com.devpt.collapsar.util.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by chenyong on 2016/6/7.
 */
@Component
public class UserCheckHandler {
    private static final Logger logger = LoggerFactory.getLogger(UserCheckHandler.class);
    private static final String USER_TOKEN_PREFIX = "user_token_";
    private static final String ACCESS_COUNT_PREFIX = "user_access_count_";
    @Autowired
    private ConfigService configService;
    @Autowired
    private StringRedisTemplate redisTemplate;


    public boolean checkUser(final int userid, final String token, final int channel){
        logger.debug("checkUser: userid={}, token={}, channel={}", userid, token, channel);

        if(StringUtils.isEmpty(token)){
            logger.error("checkUser: token is empty. token={}", token);
            return false;
        }

        //check channel
        ConfigOuterChannel outerChannel = this.configService.getConfigOuterChannel(channel);
        if(null == outerChannel){
            logger.error("checkUser: channel invalid. channel={}", channel);
            return false;
        }

        //check token
        String dstToken = this.redisTemplate.opsForValue().get(getUserTokenCacheKey(userid, channel));
        logger.info("checkUser: srcToken={}, dstToken={}", token, dstToken);
        return (null != token && token.equals(dstToken));
    }


    public String generateUserToken(int userid, int channel, int accountType) throws CollapsarException{
        logger.debug("generateUserToken: userid={}, channel={}, accountType={}", userid, channel, accountType);

        ConfigOuterChannel outerChannel = this.configService.getConfigOuterChannel(channel);
        if(null == outerChannel){
            logger.error("generateUserToken: channel invalid. channel={}", channel);
            throw new CollapsarException(ErrorCode.SYS_CHANNEL_INVALID);
        }

        final int expirePeriod = outerChannel.getTokenExpirePeriod();
        final String newToken = UUID.randomUUID().toString();
        this.redisTemplate.opsForValue().set(getUserTokenCacheKey(userid, channel), newToken, expirePeriod, TimeUnit.SECONDS);

        this.statisAccessCount(userid, channel, 60, 10);

        return newToken;
    }


    /**
     * 统计用户访问量
     * @param userid
     * @param channel 访问渠道
     * @param window 统计窗口大小（单位天）
     * @param frequency 统计平率（单位分钟，最大为60分钟）
     */
    private void statisAccessCount(int userid, int channel, int window, int frequency){
        logger.debug("statisAccessCount: userid={}, channel={}, period={}, window={}, frequency={}", userid, channel, window, frequency);
        try{
            final String stastisKey = getAccessCountCacheKey(channel, frequency);
            if(!this.redisTemplate.opsForHash().putIfAbsent(stastisKey, getUserTokenCacheKey(userid, channel), System.currentTimeMillis()/1000)){
                logger.error("statisAccessCount: inc access count fail.");
            }else {
                this.redisTemplate.expire(stastisKey, window, TimeUnit.DAYS);
            }
        }catch (Exception ex){
            logger.error("statisAccessCount: catch exception, ex={}", ex.getMessage());
        }
    }

    private static String getAccessCountCacheKey(final int type, int frequency){
        if(frequency > 60){
            frequency = 60;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHH");
        final String perfix = format.format(new Date());
        int minute = Calendar.getInstance().get(Calendar.MINUTE);
        String suffix = (minute/frequency == 0 ? "00" : "" + (minute%frequency * frequency));

        StringBuilder countKey = new StringBuilder(ACCESS_COUNT_PREFIX);
        countKey.append(type).append("_");
        countKey.append(perfix).append(suffix);

        return countKey.toString();
    }


    private static String getUserTokenCacheKey(int userid, int channel){
        StringBuilder tokenKey = new StringBuilder(USER_TOKEN_PREFIX);
        tokenKey.append(userid).append("_").append(channel);
        return tokenKey.toString();
    }



}
