package com.dkforum.dto.request.auth;


import com.fasterxml.jackson.annotation.JsonProperty;

public class RefreshDTO {

    @JsonProperty("token")
    private String token;

    public RefreshDTO() {

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
