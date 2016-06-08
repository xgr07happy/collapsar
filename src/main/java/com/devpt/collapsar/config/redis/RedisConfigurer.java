package com.devpt.collapsar.config.redis;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * Created by chenyong on 2016/6/8.
 */
@Configuration
@EnableCaching
public class RedisConfigurer extends CachingConfigurerSupport {

}
