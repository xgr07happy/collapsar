package com.devpt.collapsar.config.task;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by chenyong on 2016/5/31.
 */
@Configuration
@EnableScheduling
public class MySchedulingConfigurer implements SchedulingConfigurer{
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskScheduler());
    }

    @Bean (destroyMethod = "shutdown")
    public Executor taskScheduler(){
        return Executors.newScheduledThreadPool(42);
    }
}
