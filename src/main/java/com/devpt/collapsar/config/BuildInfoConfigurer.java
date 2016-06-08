package com.devpt.collapsar.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by chenyong on 2016/1/14.
 */
@Component
@ConfigurationProperties(prefix="build")
public class BuildInfoConfigurer {
    private String module;
    private String revision;
    private String tag;
    private String datetime;
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
        return "BuildInfoConfigurer{" +
                "module='" + module + '\'' +
                ", revision='" + revision + '\'' +
                ", tag='" + tag + '\'' +
                ", datetime='" + datetime + '\'' +
                ", hostname='" + hostname + '\'' +
                '}';
    }
}
