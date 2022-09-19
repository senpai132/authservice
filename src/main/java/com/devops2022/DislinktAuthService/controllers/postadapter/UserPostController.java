package com.devops2022.DislinktAuthService.controllers.postadapter;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.devops2022.DislinktAuthService.helper.dto.postDTOs.UserPostDTO;

@RestController
@CrossOrigin
@RequestMapping("/postadapter")
public class UserPostController {

    private RestTemplate restTemplate;
    private String baseurl = "http://localhost:8001";

    @PostMapping("/saveUserPost")
    public ResponseEntity<String> addUserPost(@RequestBody UserPostDTO dto) {
        try {
            HttpEntity<UserPostDTO> request = new HttpEntity<>(dto);
            ResponseEntity<String> response = restTemplate.exchange(baseurl+"/saveUserPost", HttpMethod.POST, request, String.class);
            return response;
        }
        catch (Exception e) {
            return new ResponseEntity<>("Error occurred while adding new post", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllUserPosts")
    public ResponseEntity<String> getAllPosts() {         
        try {        
            ResponseEntity<String> entity = restTemplate.getForEntity(baseurl+"/getAllUserPosts", String.class);
            return entity;       
        } catch (Exception e) {
            return new ResponseEntity<>("Error occurred while retrieving posts", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public UserPostController() {
        this.restTemplate = new RestTemplate();
    }
}
