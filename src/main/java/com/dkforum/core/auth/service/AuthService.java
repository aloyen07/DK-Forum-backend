package com.dkforum.core.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.dkforum.core.model.RefreshToken;
import com.dkforum.core.model.UserEntity;
import com.dkforum.dto.request.LoginDTO;
import com.dkforum.dto.request.RefreshDTO;
import com.dkforum.dto.response.AuthResponse;
import com.dkforum.dto.response.RefreshTokenResponse;
import com.dkforum.exception.ApiException;
import com.dkforum.exception.ConflictException;
import com.dkforum.dto.request.RegisterDTO;

import com.dkforum.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Value("${app.secret}")
    private String JWT_SECRET;

    public AuthResponse register(RegisterDTO data, HttpServletRequest request) throws ApiException {
        Optional<UserEntity> user = userService.find(data.getEmail(), data.getUsername());
        System.out.println(JWT_SECRET);
        if(user.isPresent()){
            throw new ConflictException("Such user already registered");
        }

        UserEntity newUserEntity = userService.create(data.getEmail(), data.getUsername(), data.getPassword());

        Pair<String,String> tokens = tokenService.generateTokens(newUserEntity, request.getHeader("User-Agent") , request.getRemoteAddr());

        return new AuthResponse(tokens.getFirst(), tokens.getSecond(), newUserEntity);
    }

    public AuthResponse login(LoginDTO data, HttpServletRequest request) throws ApiException{
        Optional<UserEntity> userSearchResult = userService.find(data.getLogin());

        if(userSearchResult.isEmpty()){
            throw new UnauthorizedException("Invalid credentials");
        }

        UserEntity userEntity = userSearchResult.get();

        if(!BCrypt.verifyer().verify(data.getPassword().toCharArray(), userEntity.getPassword()).verified){
            //Auth failed
            throw new UnauthorizedException("Invalid credentials");
        }

        //Authorised. Generating tokens;
        Pair<String,String> tokens = tokenService.generateTokens(userEntity, request.getHeader("User-Agent") , request.getRemoteAddr());

        return new AuthResponse(tokens.getFirst(), tokens.getSecond(), userEntity);
    }

    public RefreshTokenResponse refresh(RefreshDTO data, HttpServletRequest request) throws ApiException {
        Optional<RefreshToken> token = tokenService.findActive(data.getToken(), request);

        if(token.isPresent()){
            return new RefreshTokenResponse(
                    tokenService.generateTokens(token.get().getUser(), token.get().getApplication(), token.get().getAddress())
            );
        }else throw new UnauthorizedException("Token expired or invalid");
    }

}
