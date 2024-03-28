package com.ultimate.bank.config;

import com.ultimate.bank.exception.BadCredentialsException;
import com.ultimate.bank.exception.CPFIsNotUniqueException;
import com.ultimate.bank.exception.EmailIsNotUniqueException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CPFIsNotUniqueException.class)
    public ResponseEntity<String> handleEmailIsNotUnique(CPFIsNotUniqueException ex) {
        return ResponseEntity.status(400).body(ex.getMessage());
    }

    @ExceptionHandler(EmailIsNotUniqueException.class)
    public ResponseEntity<String> handleCustomerNotFound(EmailIsNotUniqueException ex) {
        return ResponseEntity.status(400).body(ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentials(BadCredentialsException ex) {
        return ResponseEntity.status(401).body(ex.getMessage());
    }
}