package com.dkforum.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends ApiException{

    public ConflictException(String message) {
        super("ERR_CONFLICT", message, HttpStatus.CONFLICT);
    }

    public ConflictException() {
        this("Conflict! Such resource already exists!");
    }
}
