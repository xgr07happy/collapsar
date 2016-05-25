package com.devpt.collapsar.service.common;

import com.devpt.collapsar.model.common.VendorConnectParams;
import com.devpt.collapsar.util.MyUtils;
import com.devpt.collapsar.util.TokenHelper;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by chenyong on 2016/5/23.
 */
public class VendorHttpClientService {
    private static final Logger logger = LoggerFactory.getLogger(VendorHttpClientService.class);
    public static final String AUTH_TOKEN = "X_AUTH_TOKEN";
    public static final String TOKEN_VERSION = "V1";
    public static final String UTF8_ENCODE = "UTF-8";
    private static final int PERIOD = 10;
    private static List<String> ALLOWED_METHODS = Arrays.asList("GET", "POST", "PUT", "DELETE", "HEAD");

    private VendorConnectParams connectParams;
    private RestTemplate restTemplate;

    private static class HeaderRequestInterceptor implements ClientHttpRequestInterceptor {
        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                            ClientHttpRequestExecution execution) throws IOException {
            HttpRequest wrapper = new HttpRequestWrapper(request);
            wrapper.getHeaders().set("Accept-charset", UTF8_ENCODE);
            return execution.execute(wrapper, body);
        }
    }

    public VendorHttpClientService(VendorConnectParams connectParams) {
        this.connectParams = connectParams;
        logger.debug("connectParams connection: {}", connectParams);
        // work-around to fix accept-charset issue of rest template

        this.restTemplate = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptorsTimeout = new ArrayList<ClientHttpRequestInterceptor>();
        interceptorsTimeout.add(new HeaderRequestInterceptor());
        this.restTemplate.setInterceptors(interceptorsTimeout);
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setReadTimeout(10000);
        requestFactory.setConnectTimeout(2000);
        this.restTemplate.setRequestFactory(requestFactory);
    }

    public <T> T get(String apiurl, Class<T> clazz) {
        String url = String.format("%s/%s", connectParams.getUrlBase(), apiurl);
        logger.debug("vendor get url = {}", url);
        return restTemplate.getForObject(url, clazz);
    }

    public <T> T post(String apiUrl, Object body, Class<T> clazz) {
        return post(apiUrl, body, clazz, "POST");
    }

    public <T> T post(String apiUrl, Object body, Class<T> clazz, String method) {
        final int expireTime = MyUtils.getCurrentTimeStamp() + PERIOD * 60;
        return post(apiUrl, body, clazz, method, expireTime);
    }

    public <T> T post(String apiUrl, Object body, Class<T> clazz, String method, int expireTime) {
        logger.debug("vendor post: apiUrl={},body={},clazz={},method={},expireTime={}", apiUrl, body, clazz, method, expireTime);
        if (!StringUtils.hasText(apiUrl) || clazz == null) {
            return null;
        }
        String jsonBody = new Gson().toJson(body);
        if (body == null) {
            logger.info("vendor post: JSON body is null.");
            jsonBody = "{}";
        }
        HttpHeaders headers = new HttpHeaders();
        String queryParam = "";
        if (apiUrl.lastIndexOf("?") != -1) {
            queryParam = apiUrl.substring(apiUrl.lastIndexOf("?") + 1, apiUrl.length());
            apiUrl = apiUrl.substring(0, apiUrl.lastIndexOf("?"));
        }
        TokenHelper tokenHelper = new TokenHelper(connectParams.getAccessKey(), connectParams.getSecretKey());
        headers.set(AUTH_TOKEN, tokenHelper.generateToken(apiUrl, method, queryParam, jsonBody, expireTime));
        headers.set("Content-type", "application/json;charset=utf-8");
        //
        final String url = String.format("%s%s", connectParams.getUrlBase(), apiUrl);
        logger.debug("vendor post url = {}", url);
        //
        HttpEntity<String> entity = new HttpEntity<String>(jsonBody, headers);
        return restTemplate.postForObject(url, entity, clazz);
    }


}
