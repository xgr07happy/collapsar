package com.devpt.collapsar.config;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by chenyong on 2016/1/14.
 */
//@ConfigurationProperties(prefix="build", locations="classpath:buildinfo.properties")
@Component
public class BuildInfoConfigurer {
    @Value("${build.module}")
    private String module;
    @Value("${build.revision}")
    private String revision;
    @Value("${build.tag}")
    private String tag;
    @Value("${build.datetime}")
    private String datetime;
    @Value("${build.hostname}")
    private String hostname;


    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
