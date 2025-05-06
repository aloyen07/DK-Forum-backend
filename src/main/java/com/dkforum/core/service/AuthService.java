package com.dkforum.core.service;

import com.dkforum.core.model.User;
import com.dkforum.dto.response.AuthResponse;
import com.dkforum.exception.ApiException;
import com.dkforum.exception.ConflictException;
import com.dkforum.dto.request.RegisterDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    public AuthResponse register( RegisterDTO data) throws ApiException {
        Optional<User> user = userService.find(data.getEmail());

        if(user.isPresent()){
            throw new ConflictException("Such user already registered");
        }

        User newUser = userService.create(data.getEmail(), data.getUsername(), data.getPassword());

        return new AuthResponse("not implemented", "not implemented", newUser);
    }

    public void login(){

    }

}
