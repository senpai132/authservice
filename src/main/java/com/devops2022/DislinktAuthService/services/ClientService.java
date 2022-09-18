package com.devops2022.DislinktAuthService.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.devops2022.DislinktAuthService.model.Authority;
import com.devops2022.DislinktAuthService.model.User;
import com.devops2022.DislinktAuthService.repositories.AuthorityRepository;
import com.devops2022.DislinktAuthService.repositories.UserRepository;

@Service
public class ClientService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /*public User findUserByUserId(String username) {
    	return clientRepository.findByStudentData_Id(username);
    }*/
    
    public User registerUser(User userData) throws Exception{
        if(userRepository.findByUsername(userData.getUsername()) != null){
            throw new Exception("User with given username already exists");
        }

        User student = new User();
        student.setUsername(userData.getUsername());
        student.setLastPasswordResetDate(new Timestamp(System.currentTimeMillis() - 1000));
        List<Authority> authorities = new ArrayList<Authority>();

        authorities.add(authorityRepository.findByName("ROLE_CLIENT"));
        student.setAuthorities(authorities);
        student.setPassword(passwordEncoder.encode(userData.getPassword()));
        userRepository.save(student);
        return student;
    }

    public User editUser(String username, User userData) throws Exception{
        if(!username.equals(userData.getUsername()) &&
            (userRepository.findByUsername(userData.getUsername()) != null)) {
                throw new Exception("User with given username already exists");
        }

        User student = userRepository.findByUsername(username);
        student.setUsername(userData.getUsername());
        userRepository.save(student);
        
        return student;
    }
}
