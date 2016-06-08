package com.devpt.collapsar.controller;

import com.devpt.collapsar.config.access.UserCheck;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by chenyong on 2016/6/8.
 */
@RestController
public class UserCheckController {

    @UserCheck
    @RequestMapping(value = "/userCheck")
    public Object check(){
        System.out.print("check user.");
        return "check end.";
    }
}
