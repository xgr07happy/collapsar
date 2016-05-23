package com.devpt.collapsar.configure.mybatis;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by chenyong on 2016/5/17.
 */
@Configuration
@AutoConfigureAfter(MyBatisConfigurer.class)
public class MyBatisMapperScannerConfigurer {

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.devpt.*.dao.mapper");
        return mapperScannerConfigurer;
    }
}
