package com.dkforum.core.repository;

import com.dkforum.core.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsernameOrEmail(String userName, String email);

}
