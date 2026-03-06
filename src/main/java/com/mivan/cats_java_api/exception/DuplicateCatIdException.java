package com.mivan.cats_java_api.exception;

public class DuplicateCatIdException extends RuntimeException {

    public DuplicateCatIdException(String message) {
        super(message);
    }
}
