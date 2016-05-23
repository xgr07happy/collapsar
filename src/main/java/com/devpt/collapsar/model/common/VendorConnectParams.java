package com.devpt.collapsar.model.common;


/**
 * Created by chenyong on 2016/5/23.
 */
public class VendorConnectParams {
    private String accessKey;
    private String secretKey;
    private String urlBase;

    public VendorConnectParams(){}

    public VendorConnectParams(String urlBase, String accessKey, String secretKey){
        this.urlBase = urlBase;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getUrlBase() {
        return urlBase;
    }

    public void setUrlBase(String urlBase) {
        this.urlBase = urlBase;
    }

    @Override
    public String toString() {
        return "VendorConnectParams{" +
                "accessKey='" + accessKey + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", urlBase='" + urlBase + '\'' +
                '}';
    }
}
