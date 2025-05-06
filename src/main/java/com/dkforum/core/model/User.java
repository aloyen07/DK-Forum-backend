package com.dkforum.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "users")
public class User {

    public User() {
    }

    public User(String email, String username, String realname, String password){
        this.username = username;
        this.email = email;
        this.password = password;
        this.realname = realname;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Integer id;

    @Column()
    @JsonProperty("uuid")
    private String uuid;

    @Column(unique = true, nullable = false)
    @JsonProperty("email")
    private String email;
    @Column(unique = true, nullable = false)
    @JsonProperty("username")
    private String username;

    @Column(nullable = false)
    @JsonProperty("realname")
    private String realname;

    @Column(nullable = false)
    private String password;


}
