package com.dkforum.core.repository;

import com.dkforum.core.model.RefreshToken;
import com.dkforum.core.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<RefreshToken, String> {

    Optional<RefreshToken> findByTokenAndAddressAndApplicationAndExpiredAfter(String token, String address, String application, Instant expired);

    void deleteByAddressAndApplicationAndUser(String address, String application, UserEntity userEntity);
}
