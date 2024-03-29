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
@RequestMapping("/dislikepostadapter")
public class DislikePostController {
    private RestTemplate restTemplate;
    private final String baseurl = "http://post-service:8001/";
    private static final Logger LOGGER = LoggerFactory.getLogger(DislikePostController.class);

    @PostMapping("/saveDislikePost")
    public ResponseEntity<String> likePost(@RequestBody LikePostDTO dto) {
        try {
            LOGGER.info("Added dislike to post " + dto.getUserPostID() + " by user " + dto.getUsername());
            HttpEntity<LikePostDTO> request = new HttpEntity<>(dto);
            ResponseEntity<String> response = restTemplate.exchange(baseurl+"/saveDislikePost", HttpMethod.POST, request, String.class);
            return response;
        }
        catch (Exception e) {
            LOGGER.error("Error occurred while adding disliking a post");
            return new ResponseEntity<>("Error occurred while adding disliking a post", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/deleteDislikePost/{id}")
    public ResponseEntity<String> unlikePost(@PathVariable Long id) {         
        try {
            LOGGER.info("Removing dislike of post");
            ResponseEntity<String> entity = restTemplate.getForEntity(baseurl+"/deleteDislikePost/"+id, String.class);
            return entity;       
        } catch (Exception e) {
            LOGGER.error("Error occurred while undisliking a post");
            return new ResponseEntity<>("Error occurred while undisliking a post", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllDislikePosts")
    public ResponseEntity<String> getAllPosts() {         
        try {
            LOGGER.info("Get all dislikes of posts");
            ResponseEntity<String> entity = restTemplate.getForEntity(baseurl+"/getAllDislikePosts", String.class);
            return entity;       
        } catch (Exception e) {
            LOGGER.error("Error occurred while retrieving dislikes");
            return new ResponseEntity<>("Error occurred while retrieving dislikes", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public DislikePostController() {
        this.restTemplate = new RestTemplate();
    }
}
