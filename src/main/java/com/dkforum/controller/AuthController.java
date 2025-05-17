package com.dkforum.controller;

import com.dkforum.core.auth.ann.Auth;
import com.dkforum.core.auth.ann.User;
import com.dkforum.core.model.UserEntity;
import com.dkforum.dto.request.auth.ForgotPasswordDTO;
import com.dkforum.dto.request.auth.LoginDTO;
import com.dkforum.dto.request.auth.RefreshDTO;
import com.dkforum.dto.request.auth.RegisterDTO;


import com.dkforum.exception.ApiException;
import com.dkforum.core.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO registerDTO, HttpServletRequest request) {
        try{
            return ResponseEntity.ok(service.register(registerDTO, request));
        }catch (ApiException e){
            return new ResponseEntity<>(e, e.getStatus());
        }
    }

    @PostMapping(value = "/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO loginDto, HttpServletRequest request){
        try{
            return ResponseEntity.ok(service.login(loginDto, request));
        }catch (ApiException e){
            return new ResponseEntity<>(e, e.getStatus());
        }
    }

    @PostMapping(value = "/refresh")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> refresh(@RequestBody @Valid RefreshDTO tokenDto, HttpServletRequest request) {
        try{
            return ResponseEntity.ok(service.refresh(tokenDto, request));
        }catch (ApiException e){
            return new ResponseEntity<>(e, e.getStatus());
        }
    }

    @PostMapping(value = "/forgot")
    @ResponseStatus(HttpStatus.OK)
    public void forgot(@RequestBody @Valid ForgotPasswordDTO data) {

    }

    @Auth
    @GetMapping(value = "/profile")
    public ResponseEntity<?> profile(@User UserEntity user) {
        return ResponseEntity.ok(user);
    }

}
