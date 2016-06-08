package com.devpt.collapsar.controller;

import com.devpt.collapsar.config.BuildInfoConfigurer;
import com.devpt.collapsar.config.access.UserCheck;
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
    private BuildInfoConfigurer buildInfoConfigure;

    @UserCheck
    @RequestMapping("/appver")
    public Object appVersion() {
        logger.info("appver: "+ this.buildInfoConfigure.toString());
        return this.buildInfoConfigure;
    }

}
