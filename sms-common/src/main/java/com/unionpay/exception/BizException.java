package com.unionpay.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class BizException extends RuntimeException {
    private Integer status = BAD_REQUEST.value();

    public BizException(String msg){
        super(msg);
    }

    public BizException(HttpStatus status, String msg){
        super(msg);
        this.status = status.value();
    }
}
