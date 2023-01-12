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

import com.devops2022.DislinktAuthService.helper.dto.postDTOs.CommentDTO;

@RestController
@CrossOrigin
@RequestMapping("/commentadapter")
public class CommentController {

    private RestTemplate restTemplate;
    private String baseurl = "http://post-service:8001";

    @PostMapping("/saveComment")
    public ResponseEntity<String> addUserPost(@RequestBody CommentDTO dto) {
        try {
            HttpEntity<CommentDTO> request = new HttpEntity<>(dto);
            ResponseEntity<String> response = restTemplate.exchange(baseurl+"/saveComment", HttpMethod.POST, request, String.class);
            return response;
        }
        catch (Exception e) {
            return new ResponseEntity<>("Error occurred while adding new comment", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllComments")
    public ResponseEntity<String> getAllPosts() {         
        try {        
            ResponseEntity<String> entity = restTemplate.getForEntity(baseurl+"/getAllComments", String.class);
            return entity;       
        } catch (Exception e) {
            return new ResponseEntity<>("Error occurred while retrieving comments", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public CommentController() {
        this.restTemplate = new RestTemplate();
    }
    
}
