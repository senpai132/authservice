package com.devops2022.DislinktAuthService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.devops2022.DislinktAuthService.model.User;
import com.devops2022.DislinktAuthService.repositories.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@SpringBootTest
class DislinktAuthServiceApplicationTests {
	@Autowired
	private UserRepository userRepository;

	@Test
	void testGetUserByUsername() {
		User u = userRepository.findByUsername("admin");
		assertThat(u).isNotNull();
	}

	@Test
	void testGetAllUsers() {
		List<User> users = userRepository.findAll();
		assertThat(users.size()).isGreaterThan(0);
	}

}
