package com.dkforum.controller;

import com.dkforum.dto.request.RegisterDTO;


import com.dkforum.exception.ApiException;
import com.dkforum.core.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping(value = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO registerDTO) {
        try{
            return ResponseEntity.ok(service.register(registerDTO));
        }catch (ApiException e){
            return new ResponseEntity<>(e, e.getStatus());
        }
    }
}
