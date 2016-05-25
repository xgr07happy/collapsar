package com.devpt.collapsar.controller;

import com.devpt.collapsar.model.common.GenericResponse;
import com.devpt.collapsar.model.common.VendorConnectParams;
import com.devpt.collapsar.model.rbac.QueryUsersByPageReq;
import com.devpt.collapsar.service.common.VendorHttpClientService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by chenyong on 2016/5/23.
 */
public class RbacControllerTest {
    private static Logger logger = LoggerFactory.getLogger(RbacControllerTest.class);
    private final ThreadLocal<Long> threadLocal = new ThreadLocal<Long>();

    @Before
    public void setUp() throws Exception {
        logger.debug("begin -->");
        threadLocal.set(System.currentTimeMillis());
    }

    @After
    public void tearDown() throws Exception {
        logger.debug("end <--use time={} ms", (System.currentTimeMillis() - threadLocal.get()));
        threadLocal.remove();
    }

    @Test
    public void testQueryUsersByPage(){
        VendorConnectParams connectParams = new VendorConnectParams();
        connectParams.setUrlBase("http://localhost:8080");
        connectParams.setAccessKey("FSDFGFDGFDHFFDFDFDF");
        connectParams.setSecretKey("gfdgfdgfdgfdfffdser");
        VendorHttpClientService vendorHttpClientService = new VendorHttpClientService(connectParams);

        QueryUsersByPageReq req = new QueryUsersByPageReq();
        req.setPageIndex(1);
        req.setPageSize(10);

        GenericResponse resp = vendorHttpClientService.post("/collapsar/rbac/queryUsersByPage", req, GenericResponse.class);
        logger.info("testQueryUsersByPage: resp={}, systim={}", resp, System.currentTimeMillis());
    }
}
