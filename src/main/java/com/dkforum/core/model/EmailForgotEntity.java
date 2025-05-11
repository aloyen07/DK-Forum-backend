package com.dkforum.core.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "email_verification")
public class EmailVerification {


    private String token;
}
