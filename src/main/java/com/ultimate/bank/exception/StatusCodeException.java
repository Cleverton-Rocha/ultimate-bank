package com.ultimate.bank.exception;

import lombok.Getter;

@Getter
public class StatusCodeException extends RuntimeException{
    private final int statusCode;

    public StatusCodeException(int statusCode) {
        this.statusCode = statusCode;
    }

}
