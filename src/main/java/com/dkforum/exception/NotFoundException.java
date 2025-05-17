package com.dkforum.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ApiException{

    public NotFoundException(String message) {
        super("ERR_NOT_FOUND", message, HttpStatus.NOT_FOUND);
    }

    public NotFoundException(){
        this("Resource not found!");
    }
}
