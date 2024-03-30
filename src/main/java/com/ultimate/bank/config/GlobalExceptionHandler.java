package com.ultimate.bank.config;

import com.ultimate.bank.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IsNotUniqueException.class)
    public ResponseEntity<String> handleEmailIsNotUnique(IsNotUniqueException ex) {
        return ResponseEntity.status(400).body(ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentials(BadCredentialsException ex) {
        return ResponseEntity.status(401).body(ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(NotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(LimitExceedException.class)
    public ResponseEntity<String> handleInsufficientFunds(LimitExceedException ex) {
        return ResponseEntity.status(422).body(ex.getMessage());
    }

    @ExceptionHandler(StatusCodeException.class)
    public ResponseEntity<String> handleStatusCodeException(StatusCodeException ex) {
        return ResponseEntity.status(ex.getStatusCode()).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(400)
                             .body(Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage());
    }
}