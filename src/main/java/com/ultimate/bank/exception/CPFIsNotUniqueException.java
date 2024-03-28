package com.ultimate.bank.exception;

public class CPFIsNotUniqueException extends RuntimeException {
    public CPFIsNotUniqueException(String message) {
        super(message);
    }
}
