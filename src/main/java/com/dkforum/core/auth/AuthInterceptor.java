package com.dkforum.ann;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dkforum.Application;
import com.dkforum.core.model.UserEntity;
import com.dkforum.core.repository.UserRepository;
import com.dkforum.core.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;


@Component
public class AuthInterceptor implements HandlerInterceptor {

    private UserRepository userRepository;
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(userRepository == null) userRepository = Application.getContext().getBean(UserRepository.class);
        if(tokenService == null) tokenService = Application.getContext().getBean(TokenService.class);

        Auth ann = ((HandlerMethod)handler).getMethodAnnotation(Auth.class);

        if(ann == null) return true;

        //Auth processing
        if(request.getHeader("Authorization") == null) {
            //No Auth Header provided
//            System.out.println("Request to protected " + request.getMethod() + " without providing Authorization header from " + request.getRemoteAddr());
            response.sendError(403);
            return false;
        }

        String token = request.getHeader("Authorization").split(" ")[1];

        System.out.println("Provided token: " + token);

        DecodedJWT data;
        try{
            data = tokenService.verify(token);
        }catch (JWTVerificationException e){
            //Token Expired
            response.sendError(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        Optional<UserEntity> user = userRepository.findById(data.getClaim("_id").asInt());

        if(user.isEmpty()){
            //No such user exists anymore
            response.sendError(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        request.setAttribute("user", user.get());

        return true;
    }

}
