package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomBaseException.class)
    public ResponseEntity<SimpleResponse> handleProductNotFoundException(CustomBaseException exception) {
        return ResponseEntity.status(exception.getStatus()).body(exception.getSimpleResponse());
    }
}
