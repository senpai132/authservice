package com.devops2022.DislinktAuthService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devops2022.DislinktAuthService.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
