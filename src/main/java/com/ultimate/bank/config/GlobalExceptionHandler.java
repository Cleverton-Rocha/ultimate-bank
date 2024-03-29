package com.ultimate.bank.config;

import com.ultimate.bank.exception.BadCredentialsException;
import com.ultimate.bank.exception.CPFIsNotUniqueException;
import com.ultimate.bank.exception.EmailIsNotUniqueException;
import com.ultimate.bank.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

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

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(400).body(Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage());
    }
}