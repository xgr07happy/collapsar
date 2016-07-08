package com.devpt.collapsar.service.task;

import com.devpt.collapsar.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by chenyong on 2016/5/30.
 */
@Component
public class SampleTasks {
    @Autowired
    SampleService sampleService;

    //@Scheduled(cron = "*/3 * * * * ?")
    void task(){
        System.out.println("##start a task." + Thread.currentThread().getName());
        //sampleService.doSomethingTimeConsuming();
        this.doSomethingTimeConsuming();
        System.out.println("##finish a task." + Thread.currentThread().getName());
    }

    @Async
    public void doSomethingTimeConsuming() {
        System.out.println("start doing sth waste time." + Thread.currentThread().getName());
        try{
            Thread.currentThread().sleep(5000);
        }catch (InterruptedException ex){
            ex.printStackTrace();
        }
        System.out.println("finish doing sth waste time." + Thread.currentThread().getName());
    }

}
