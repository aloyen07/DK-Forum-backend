package com.dkforum.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {

    public RefreshToken() {
    }

    public RefreshToken(String token, String application, String address, UserEntity user) {
        this.token = token;
        this.application = application;
        this.address = address;
        this.user = user;

        this.expired = Instant.now().plus(30, ChronoUnit.DAYS);
    }

    @Id
    @JsonProperty("token")
    private String token;

    @JsonProperty("application")
    @Column(nullable = false)
    private String application;

    @JsonProperty("address")
    @Column(nullable = false)
    private String address;


    @Column(nullable = false, name = "expired_at")
    @JsonIgnore
    private Instant expired;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name="user")
    private UserEntity user;


    public String getApplication() {
        return application;
    }

    public String getAddress() {
        return address;
    }

    public UserEntity getUser() {
        return user;
    }

    public Instant getExpired() {
        return expired;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
