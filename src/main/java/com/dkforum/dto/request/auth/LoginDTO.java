package com.dkforum.dto.request.auth;


import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginDTO {

    public LoginDTO() {
    }

    @JsonProperty("login")
    private String login;
    @JsonProperty("password")
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
