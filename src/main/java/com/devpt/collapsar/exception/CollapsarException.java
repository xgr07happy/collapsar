package com.devpt.collapsar.exception;

/**
 * Created by chenyong on 2016/5/16.
 */
public class CollapsarException extends RuntimeException{
    private int errorCode;

    public CollapsarException(){
        super();
    }

    public CollapsarException(int errorCode){
        this.errorCode = errorCode;
    }

    public int getErrorCode(){
        return this.errorCode;
    }
}
