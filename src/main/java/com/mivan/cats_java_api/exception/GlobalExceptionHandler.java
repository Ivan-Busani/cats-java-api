package com.mivan.cats_java_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateCatIdException.class)
    public ResponseEntity<Map<String, String>> handleDuplicate(DuplicateCatIdException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("detail", e.getMessage()));
    }

    @ExceptionHandler(CatNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(CatNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("detail", e.getMessage()));
    }
}