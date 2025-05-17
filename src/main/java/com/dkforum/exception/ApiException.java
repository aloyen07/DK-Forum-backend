package com.dkforum.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


public class ApiException extends Exception{
    @JsonIgnore
    private final Throwable cause = null;
    @JsonIgnore
    private final StackTraceElement[] stackTrace = new StackTraceElement[0];
    @JsonIgnore
    private final String localizedMessage= "";
    @JsonIgnore
    private final String[] suppressed = new String[0];

    @JsonProperty("type")
    private final String type;
    @JsonProperty("message")
    private String message;

    @JsonIgnore
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
