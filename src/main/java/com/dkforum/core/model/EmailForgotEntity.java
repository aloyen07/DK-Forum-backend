package com.dkforum.core.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "email_verification")
public class EmailForgotEntity {

    public EmailForgotEntity(String token) {
        this.token = token;
    }

    public EmailForgotEntity() {
    }

    private String token;
}
