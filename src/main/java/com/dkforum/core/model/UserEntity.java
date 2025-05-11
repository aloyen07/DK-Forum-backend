package com.dkforum.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    public User() {
    }

    public User(String email, UUID uuid, String username, String realname, String password){
        this.username = username;
        this.email = email;
        this.password = password;
        this.realname = realname;
        this.uuid = uuid;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Integer id;

    @Column()
    @JsonProperty("uuid")
    private UUID uuid;

    @Column(unique = true, nullable = false)
    @JsonProperty("email")
    private String email;
    @Column(unique = true, nullable = false)
    @JsonProperty("username")
    private String username;

    @Column(nullable = false)
    @JsonProperty("realname")
    private String realname;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @JsonIgnore()
    @OneToMany(mappedBy = "user")
    private List<RefreshToken> sessions;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<RefreshToken> getSessions() {
        return sessions;
    }

    public void setSessions(List<RefreshToken> sessions) {
        this.sessions = sessions;
    }
}
