package com.rukayun.bff.config;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rukayun.bff.exceptions.PassthroughException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PassthroughException.class)
    public ResponseEntity<String> handlePassthrough(PassthroughException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(ex.getBody());
    }
}