package com.dkforum.core.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dkforum.core.model.RefreshToken;
import com.dkforum.core.model.UserEntity;
import com.dkforum.core.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class TokenService {

    private static TokenService i;
    public static TokenService i(){return i;}

    @Autowired
    private TokenRepository tokenRepository;

    private final Algorithm algorithm;

    public TokenService(@Value("${app.secret}") String secret) {
        this.algorithm = Algorithm.HMAC512(secret);
        i = this;
    }

    @Transactional
    public Pair<String, String> generateTokens(UserEntity userEntity, String application, String address){
        String accessToken = JWT.create()
                .withClaim("_id", userEntity.getId())
                .withExpiresAt(Instant.now().plusSeconds(10*60))
                .sign(algorithm);
        String refreshToken =  UUID.randomUUID().toString().replaceAll("-", "");

        tokenRepository.deleteByAddressAndApplicationAndUser(address, application, userEntity);
        RefreshToken token = tokenRepository.save(new RefreshToken(refreshToken, application, address, userEntity));

        return Pair.of(accessToken, token.getToken());
    }

    public DecodedJWT verify(String token) throws JWTVerificationException {
        return JWT.require(algorithm).build().verify(token);
    }

    public Optional<RefreshToken> findActive(String token, HttpServletRequest request){
        return tokenRepository.findByTokenAndAddressAndApplicationAndExpiredAfter(token, request.getRemoteAddr(), request.getHeader("User-Agent"), Instant.now());
    }
}
