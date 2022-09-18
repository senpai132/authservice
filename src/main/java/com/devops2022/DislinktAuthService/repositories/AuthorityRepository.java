package com.devops2022.DislinktAuthService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devops2022.DislinktAuthService.model.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByName(String name);
}
