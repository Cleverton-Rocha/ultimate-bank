package com.ultimate.bank.exception;

public class EmailIsNotUniqueException extends RuntimeException {
    public EmailIsNotUniqueException(String message) {
        super(message);
    }
}
