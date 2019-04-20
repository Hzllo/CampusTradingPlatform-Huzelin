package com.tradingPlatform.vo;

import java.io.Serializable;

public class ResultInfo implements Serializable {

    private boolean status;
    private String message;
    private Object object;

    public ResultInfo(boolean status, String message, Object object) {
        this.status = status;
        this.message = message;
        this.object = object;
    }

    public static ResultInfo success(String message, Object object) {
        return new ResultInfo(true, message, object);
    }

    public static ResultInfo failure(String message) {
        return new ResultInfo(false, message, null);
    }

    public boolean isStatus() {
        return status;
    }

    public ResultInfo setStatus(boolean status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResultInfo setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getObject() {
        return object;
    }

    public ResultInfo setObject(Object object) {
        this.object = object;
        return this;
    }
}
