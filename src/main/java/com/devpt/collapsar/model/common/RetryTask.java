package com.devpt.collapsar.model.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by chenyong on 2016/7/6.
 *  the default retry task
 */
public class RetryTask {
    private int id;
    private String gid;
    private int createTime;
    private int updateTime;

    private int type;
    private String context;
    private int status;
    private int nextTime;
    private int retryNum;


    // task status
    public static final int STATUS_UNKNOWN = 0; // the taks is undergoing and will be scheduled in the next timeout
    public static final int STATUS_SUCCESS = 1; // the task is already succeeded
    public static final int STATUS_FAIL = 2; // transaction fail
    public static final int STATUS_SERVICE_UNAVAILABLE = 3; // the server is temporarily unavailable and will retry later
    public static final int STATUS_BAD_REQUEST = 4;  // the request is bad now (maybe auth key changed or api protocol is mismatched). the task will be dropped and never is schedule again
    public static final int STATUS_EXCEED_MAX_TIMEOUT = 5; // the task exceeded the max timeout and is dropped


    //task max retry num
    private static final int DEFAULT_MAX_RETRY_NUM = 10;



    public void process(){
        int ret = 0;
        try{
            System.out.println("process: mock process task...");
            Random random = new Random();
            ret = random.nextInt(10);
            Thread.currentThread().sleep(5000);
        }catch (InterruptedException ex){
            ex.printStackTrace();
        }

        switch (ret){
            case RetryTask.STATUS_SUCCESS:
                this.setStatus(RetryTask.STATUS_SUCCESS);
                break;
            case RetryTask.STATUS_FAIL:
                this.setStatus(RetryTask.STATUS_FAIL);
                break;
            case RetryTask.STATUS_BAD_REQUEST:
                this.setStatus(RetryTask.STATUS_BAD_REQUEST);
                break;
            case RetryTask.STATUS_SERVICE_UNAVAILABLE:
                this.setStatus(RetryTask.STATUS_BAD_REQUEST);
                break;
            case RetryTask.STATUS_EXCEED_MAX_TIMEOUT:
                this.setStatus(RetryTask.STATUS_EXCEED_MAX_TIMEOUT);
                break;
            default:
                System.out.println("process: the ret of process is still unknow, and will retry again if support.");
                this.setStatus(RetryTask.STATUS_UNKNOWN);
        }
    }

    public int getNextRetryTime() {
        if (retryNum < 5) {
            // short retry
            nextTime = (int) (System.currentTimeMillis() / 1000 + 10);
        } else if (retryNum < 8) {
            // exp interval, 1m, 2m, 4m
            nextTime = (int) (System.currentTimeMillis() / 1000 + 60 * Math.pow(2.0, retryNum - 5));
        } else {
            // fix interval
            nextTime = (int) (System.currentTimeMillis() / 1000 + 60 * 5);
        }
        retryNum += 1;
        return nextTime;
    }

    public int getMaxRetryNum() {
        return DEFAULT_MAX_RETRY_NUM;
    }

    public boolean canRetry() {
        if (status == STATUS_UNKNOWN || status == STATUS_SERVICE_UNAVAILABLE) {
            return retryNum < getMaxRetryNum();
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RetryTask that = (RetryTask) o;

        if(id != this.id) return false;
        if (gid != null ? !gid.equals(that.gid) : that.gid != null) return false;
        if (type != that.type) return false;
        if (status != that.status) return false;
        if (context != null ? !context.equals(that.context) : that.context != null) return false;
        if (retryNum != that.retryNum) return false;
        if (createTime != that.createTime) return false;
        if (updateTime != that.updateTime) return false;
        return nextTime == that.nextTime;
    }


    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (gid != null ? gid.hashCode() : 0);
        result = 31 * result + status;
        result = 31 * result + type;
        result = 31 * result + (context != null ? context.hashCode() : 0);
        result = 31 * result + retryNum;
        result = 31 * result + createTime;
        result = 31 * result + updateTime;
        result = 31 * result + nextTime;
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getRetryNum() {
        return retryNum;
    }

    public void setRetryNum(int retryNum) {
        this.retryNum = retryNum;
    }

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    public int getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(int updateTime) {
        this.updateTime = updateTime;
    }

    public int getNextTime() {
        return nextTime;
    }

    public void setNextTime(int nextTime) {
        this.nextTime = nextTime;
    }

    @Override
    public String toString() {
        return "RetryTask{" +
                "id=" + id +
                ", gid='" + gid + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", type=" + type +
                ", context='" + context + '\'' +
                ", status=" + status +
                ", nextTime=" + nextTime +
                ", retryNum=" + retryNum +
                '}';
    }
}
