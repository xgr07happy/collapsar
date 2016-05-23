package com.devpt.collapsar.configure.mybatis;

import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Properties;


/**
 * Created by chenyong on 2016/5/17.
 */
@Configuration
@EnableTransactionManagement
public class MyBatisConfigurer implements TransactionManagementConfigurer{
    @Autowired
    private DataSource dataSource;

    @Bean(name="sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(){
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage("com.devpt.*.model");

        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");

        PageHelper pageHelper = new PageHelper();
        pageHelper.setProperties(properties);

        sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageHelper});

        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        try{
            sqlSessionFactoryBean.setMapperLocations(resourcePatternResolver.getResources("classpath:mapper/**/*.xml"));
            return sqlSessionFactoryBean.getObject();
        }catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}
