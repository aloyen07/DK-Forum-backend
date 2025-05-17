package com.dkforum.core.repository;

import com.dkforum.core.model.EmailForgotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailForgotRepository extends JpaRepository<EmailForgotEntity, String> {

}
