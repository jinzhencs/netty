package com.mcc.learn.netty.util.exception;

import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by Mingchenchen on 2017/9/21
 */
public class HttpCallException extends MException {
    private int errCode;
    private String message;

    public HttpCallException() {
        super();
    }

    public HttpCallException(Throwable cause) {
        super(cause);
    }

    public HttpCallException(String message) {
        super(message);
    }

    public HttpCallException(Response response) {
        super();
        this.errCode = response.code();
        try {
            this.message = response.body().string();
        } catch (IOException e) {
            // ignore
        }
    }

    public HttpCallException(int errCode, String message) {
        super(message);
        this.errCode = errCode;
        this.message = message;
    }

    public int errCode() {
        return errCode;
    }

    public String message() {
        return message;
    }
}
