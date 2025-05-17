package com.dkforum.core.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.dkforum.core.model.UserEntity;
import com.dkforum.core.repository.UserRepository;
import com.dkforum.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<UserEntity> find(String email, String login){
        return userRepository.findByUsernameOrEmail(login.toLowerCase(), email);
    }
    public Optional<UserEntity> find(String login){
        return userRepository.findByUsernameOrEmail(login.toLowerCase(), login);
    }


    public UserEntity create(String email, String realName, String password){
        return userRepository.save(new UserEntity(
                email, Utils.generateMinecraftUUID(realName), realName.toLowerCase(), realName, BCrypt.withDefaults().hashToString(12, password.toCharArray())
        ));
    }

}
