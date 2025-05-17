package com.dkforum.core.auth.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.dkforum.core.model.EmailForgotEntity;
import com.dkforum.core.model.RefreshToken;
import com.dkforum.core.model.UserEntity;
import com.dkforum.core.repository.EmailForgotRepository;
import com.dkforum.core.service.UserService;
import com.dkforum.core.service.mail.MailDetails;
import com.dkforum.core.service.mail.MailService;
import com.dkforum.dto.request.auth.LoginDTO;
import com.dkforum.dto.request.auth.RefreshDTO;
import com.dkforum.dto.response.auth.AuthResponse;
import com.dkforum.dto.response.auth.RefreshTokenResponse;
import com.dkforum.exception.ApiException;
import com.dkforum.exception.ConflictException;
import com.dkforum.dto.request.auth.RegisterDTO;

import com.dkforum.exception.NotFoundException;
import com.dkforum.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private MailService mailService;

    @Autowired
    private EmailForgotRepository emailForgotRepository;

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

    @Transactional
    public void forgotPassword(String login) throws ApiException{
        Optional<UserEntity> userSearchResult = userService.find(login);

        if(userSearchResult.isEmpty()){
            throw new NotFoundException("No user matching given data found!");
        }

        UserEntity user = userSearchResult.get();
        String token = UUID.randomUUID().toString();

        EmailForgotEntity entity = emailForgotRepository.save(
                new EmailForgotEntity(token)
        );

        MailDetails details = new MailDetails()
                        .subject("Password Recovery")
                        .content("For recover password please follow link \n\n http://localhost:8080/recovery/"+token+"\n\nIf you don't request password recovery ignore this message")
                        .recipient(user.getEmail());

        mailService.sendSimple(details);
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
