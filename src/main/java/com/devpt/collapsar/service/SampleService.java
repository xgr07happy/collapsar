package com.devpt.collapsar.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created by chenyong on 2016/5/31.
 */
@Service
public class SampleService {
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
