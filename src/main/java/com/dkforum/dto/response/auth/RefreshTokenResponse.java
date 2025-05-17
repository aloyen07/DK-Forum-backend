package com.dkforum.dto.response.auth;

import org.springframework.data.util.Pair;

public class RefreshTokenResponse {

    public RefreshTokenResponse(Pair<String,String> tokens) {
        this.refreshToken = tokens.getFirst();
        this.accessToken = tokens.getSecond();
    }

    public RefreshTokenResponse() {}

    private String refreshToken;
    private String accessToken;


    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
