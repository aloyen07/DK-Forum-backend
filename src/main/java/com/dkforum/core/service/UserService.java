package com.dkforum.core.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.dkforum.core.model.User;
import com.dkforum.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> find(String login){
        return userRepository.findByUsernameOrEmail(login.toLowerCase(), login);
    }


    public User create(String email, String realName, String password){
        return userRepository.save(new User(
                email, realName.toLowerCase(), realName, BCrypt.withDefaults().hashToString(12, password.toCharArray())
        ));
    }

}
