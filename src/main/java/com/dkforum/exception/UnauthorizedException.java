package com.dkforum.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends ApiException{

    public UnauthorizedException(String message) {
        super("ERR_AUTH_FAILED", message, HttpStatus.UNAUTHORIZED);
    }

    public UnauthorizedException(){
        this("Unauthorized!");
    }
}
