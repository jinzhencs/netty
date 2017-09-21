package com.mcc.learn.netty.util.exception;

/**
 * Created by Mingchenchen on 2017/9/21
 *
 * 统一工程Exception
 */
public class MException extends RuntimeException {

    public MException() {
        super();
    }

    public MException(String message) {
        super(message);
    }

    public MException(String message, Throwable cause) {
        super(message, cause);
    }

    public MException(Throwable cause) {
        super(cause);
    }
}
