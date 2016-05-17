package com.devpt.collapsar.controller;

import com.devpt.collapsar.configure.BuildInfoConfigure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by chenyong on 2016/5/16.
 */
@RestController
public class AppinfoController {
    private static final Logger logger = LoggerFactory.getLogger(AppinfoController.class);
    @Autowired
    private BuildInfoConfigure buildInfoConfigure;


    @RequestMapping("/appver")
    @ResponseBody
    public Object appVersion() {
        System.out.println("#####"+this.buildInfoConfigure.toString());
        return this.buildInfoConfigure;
    }

}
