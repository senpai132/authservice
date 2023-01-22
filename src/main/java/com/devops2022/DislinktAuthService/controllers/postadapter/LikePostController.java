package com.devops2022.DislinktAuthService.controllers.postadapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.devops2022.DislinktAuthService.helper.dto.postDTOs.LikePostDTO;

@RestController
@CrossOrigin
@RequestMapping("/likepostadapter")
public class LikePostController {
    private RestTemplate restTemplate;
    private final String baseurl = "http://post-service:8001/";
    private static final Logger LOGGER = LoggerFactory.getLogger(LikePostController.class);

    @PostMapping("/saveLikePost")
    public ResponseEntity<String> likePost(@RequestBody LikePostDTO dto) {
        try {
            LOGGER.info("Added like to post " + dto.getUserPostID() + " by user " + dto.getUsername());
            HttpEntity<LikePostDTO> request = new HttpEntity<>(dto);
            ResponseEntity<String> response = restTemplate.exchange(baseurl+"/saveLikePost", HttpMethod.POST, request, String.class);
            return response;
        }
        catch (Exception e) {
            LOGGER.error("Error occurred while adding liking a post");
            return new ResponseEntity<>("Error occurred while adding liking a post", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/deleteLikePost/{id}")
    public ResponseEntity<String> unlikePost(@PathVariable Long id) {         
        try {
            LOGGER.error("Removing like on post " + id);
            ResponseEntity<String> entity = restTemplate.getForEntity(baseurl+"/deleteLikePost/"+id, String.class);
            return entity;       
        } catch (Exception e) {
            LOGGER.error("Error occurred while unliking a post");
            return new ResponseEntity<>("Error occurred while unliking a post", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllLikePosts")
    public ResponseEntity<String> getAllPosts() {         
        try {
            LOGGER.info("Get all likes of posts");
            ResponseEntity<String> entity = restTemplate.getForEntity(baseurl+"/getAllLikePosts", String.class);
            return entity;       
        } catch (Exception e) {
            LOGGER.error("Error occurred while retrieving likes");
            return new ResponseEntity<>("Error occurred while retrieving likes", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public LikePostController() {
        this.restTemplate = new RestTemplate();
    }
}
