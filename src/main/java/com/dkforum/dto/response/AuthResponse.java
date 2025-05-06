package com.dkforum.dto.response;

import com.dkforum.core.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class AuthResponse {

    public AuthResponse(String accessToken, String refreshToken, User user) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.user = user;
    }

    public AuthResponse() {
    }

    @JsonProperty("accessToken")
    private String accessToken;
    @JsonProperty("refreshToken")
    private String refreshToken;

    @JsonProperty("user")
    private User user;
}
