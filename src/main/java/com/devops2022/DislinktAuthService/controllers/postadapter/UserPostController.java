package com.devops2022.DislinktAuthService.controllers.postadapter;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private String baseurl = "http://post-service:8001";
    private String profileBaseUrl = "http://profile-service:8091";

    @PostMapping("/saveUserPost")
    public ResponseEntity<String> addUserPost(@RequestBody UserPostDTO dto) {
        try {
            HttpEntity<UserPostDTO> request = new HttpEntity<>(dto);
            ResponseEntity<String> response = restTemplate.exchange(baseurl+"/saveUserPost", HttpMethod.POST, request, String.class);
            ResponseEntity<String> entity = restTemplate.exchange(
                    profileBaseUrl + "/notifications/new-post-notification/" + dto.getUsername(),
                    HttpMethod.POST, null, String.class);

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

    @GetMapping("/finallpublicusersposts")
    public ResponseEntity<String> getAllPublicUsersPosts() {         
        try {
            ResponseEntity<String> entity = restTemplate.getForEntity(profileBaseUrl + "/userprofile/findallpublicusers", String.class);
            ResponseEntity<String> entity2 = restTemplate.exchange(baseurl+"/getAllUserPosts", HttpMethod.PUT, entity, String.class);
            return entity2;
        } catch (Exception e) {
            return new ResponseEntity<>("Error occurred while retrieving posts", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/finallfollowedusersposts/{username}")
    public ResponseEntity<String> getAllFollowedUsersPosts(@PathVariable String username) {         
        try {      
            ResponseEntity<String> entity = restTemplate.getForEntity(profileBaseUrl + "/followship/findallfollowedusernames/"+username, String.class);
            ResponseEntity<String> entity2 = restTemplate.exchange(baseurl+"/getAllUserPosts", HttpMethod.PUT, entity, String.class);
            return entity2;       
        } catch (Exception e) {
            return new ResponseEntity<>("Error occurred while retrieving posts", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findspecifiedfollowedusersposts/{username}")
    public ResponseEntity<String> getAllSpecifiedUsersPosts(@PathVariable String username) {         
        try {
            ResponseEntity<String> entity2 = restTemplate.getForEntity(baseurl+"/getAllPostsForUser/"+username, String.class);
            return entity2;       
        } catch (Exception e) {
            return new ResponseEntity<>("Error occurred while retrieving posts", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public UserPostController() {
        this.restTemplate = new RestTemplate();
    }
}
