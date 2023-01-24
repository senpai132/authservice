package com.devops2022.DislinktAuthService.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.devops2022.DislinktAuthService.helper.dto.UserDTO;
import com.devops2022.DislinktAuthService.helper.dto.UserLoginDTO;
import com.devops2022.DislinktAuthService.helper.dto.UserTokenStateDTO;
import com.devops2022.DislinktAuthService.helper.dto.UserProfileDTOs.UserProfileDTO;
import com.devops2022.DislinktAuthService.helper.mappers.UserMapper;
import com.devops2022.DislinktAuthService.model.User;
import com.devops2022.DislinktAuthService.security.TokenUtils;
import com.devops2022.DislinktAuthService.services.ClientService;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    /*@Autowired
    private UserDetailsServiceImpl userDetailsService;*/

    private UserMapper userMapper;

    private RestTemplate restTemplate;

    private final String baseurl = "http://profile-service:8091/userprofile";

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/register")
    public ResponseEntity<String> addUser(@RequestBody UserProfileDTO userRequest,
                                     HttpServletRequest request) {
        try {
            LOGGER.info("Registering new user with username " + userRequest.getUsername());
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(userRequest.getUsername());
            userDTO.setPassword(userRequest.getPassword());
            clientService.registerUser(userMapper.toEntity(userDTO));
        } catch (Exception e) {
            LOGGER.error("An error occurred while registering a new user");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        HttpEntity<UserProfileDTO> requestUserProfie = new HttpEntity<>(userRequest);
        LOGGER.info("Saving a new user profile with username " + userRequest.getUsername());
        restTemplate.postForObject(baseurl+"/add", requestUserProfie, String.class);
        LOGGER.info("New user created");
        return new ResponseEntity<>("User successfully created.", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserTokenStateDTO> createAuthenticationToken(@RequestBody UserLoginDTO authenticationRequest,
                                                                       HttpServletResponse response) {
        try {
            LOGGER.info("Logging as user " + authenticationRequest.getUsername());
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = (User) authentication.getPrincipal();
            String jwt = tokenUtils.generateToken(user);
            long expiresIn = tokenUtils.getExpiredIn();

            LOGGER.info("User " + authenticationRequest.getUsername() + "logged in");
            return ResponseEntity.ok(new UserTokenStateDTO(jwt, expiresIn));
        } catch (Exception ex) {
            LOGGER.error("An error occurred while logging as user + " + authenticationRequest.getUsername());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    
    public AuthController(MeterRegistry registry) {
        this.userMapper = new UserMapper();
        this.restTemplate = new RestTemplate();
    }
}
