package com.dkforum.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


public class ApiException extends Exception{

    @JsonProperty("type")
    private final String type;
    @JsonProperty("message")
    private String message;

    private HttpStatus status;

    public ApiException(String type, String message, HttpStatus status) {
        super(message);
        this.type = type;
        this.message = message;
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
