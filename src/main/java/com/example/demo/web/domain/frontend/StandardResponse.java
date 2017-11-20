package com.example.demo.web.domain.frontend;

import java.io.Serializable;

public class StandardResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private boolean success;
    private String message;
    private Object response;

    public StandardResponse(){

    }

    public StandardResponse(boolean success) {
        this.success = success;
    }

    public StandardResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public StandardResponse(Object response) {
        this.success = true;
        this.response = response;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }
}
