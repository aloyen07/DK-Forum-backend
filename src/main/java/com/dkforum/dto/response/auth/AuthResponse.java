package com.dkforum.dto.response.auth;

import com.dkforum.core.model.UserEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class AuthResponse {

    public AuthResponse(String accessToken, String refreshToken, UserEntity userEntity) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userEntity = userEntity;
    }

    public AuthResponse() {
    }

    @JsonProperty("accessToken")
    private String accessToken;
    @JsonProperty("refreshToken")
    private String refreshToken;

    @JsonProperty("user")
    private UserEntity userEntity;
}
