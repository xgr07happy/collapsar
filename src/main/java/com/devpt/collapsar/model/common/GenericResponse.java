package com.devpt.collapsar.model.common;

/**
 * Created by chenyong on 2016/5/17.
 */
public class GenericResponse {
    private int status;
    private String message;
    private Object content;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "GenericResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", content=" + content +
                '}';
    }
}
