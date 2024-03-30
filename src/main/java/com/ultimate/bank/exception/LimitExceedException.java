package com.ultimate.bank.exception;

public class LimitExceedException extends RuntimeException {
    public LimitExceedException(String message) {
        super(message);
    }
}
