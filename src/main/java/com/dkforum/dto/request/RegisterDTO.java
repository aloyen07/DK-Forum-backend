package com.dkforum.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterDTO {

    @Email(message = "Invalid email")
    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;
    @JsonProperty("username")
    private String username;
}
