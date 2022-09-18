package com.devops2022.DislinktAuthService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devops2022.DislinktAuthService.model.User;
import com.devops2022.DislinktAuthService.model.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);
}
