package com.devops2022.DislinktAuthService.controllers.userprofileadapter;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.devops2022.DislinktAuthService.helper.dto.UserProfileDTOs.FollowResponseDTO;
import com.devops2022.DislinktAuthService.helper.dto.UserProfileDTOs.ProfileDetailsDTO;

@RestController
@CrossOrigin
@RequestMapping("/followshipadapter")
public class FollowshipCotroller {
    private RestTemplate restTemplate;
    private final String baseurl = "http://localhost:8091/followship";

    @PutMapping("/block/{initiator}")
    public ResponseEntity<String> blockUser(@PathVariable String initiator, @RequestBody ProfileDetailsDTO target) {
        try {
            HttpEntity<ProfileDetailsDTO> request = new HttpEntity<>(target);
            restTemplate.exchange(baseurl+"/block/"+initiator, HttpMethod.PUT, request, String.class);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Error occurred while blocking user", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("User " + target.getValue() + " blocked successfully.", HttpStatus.OK);
    }

    @PutMapping("/unblock/{initiator}")
    public ResponseEntity<String> unblockUser(@PathVariable String initiator, @RequestBody ProfileDetailsDTO target) {
        try {
            HttpEntity<ProfileDetailsDTO> request = new HttpEntity<>(target);
            restTemplate.exchange(baseurl+"/unblock/"+initiator, HttpMethod.PUT, request, String.class);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Error occurred while unblocking user", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("User " + target.getValue() + " unblocked successfully.", HttpStatus.OK);
    }

    @GetMapping("/isblocked/{initiator}")
    public ResponseEntity<String> isUserBlocked(@PathVariable String initiator, @RequestBody ProfileDetailsDTO target) {         
        try {
            HttpEntity<ProfileDetailsDTO> request = new HttpEntity<>(target);
            ResponseEntity<String> entity = restTemplate.exchange(baseurl+"/isblocked/"+initiator+"/"+target.getValue(), HttpMethod.GET, request, String.class);
            return entity;
        }
        catch (Exception e) {
            return new ResponseEntity<String>("Error occurred while checking block status", HttpStatus.INTERNAL_SERVER_ERROR);
        }       
    }

    @GetMapping("/isfollowed/{initiator}")
    public ResponseEntity<String> isUserFollowed(@PathVariable String initiator, @RequestBody ProfileDetailsDTO target) {         
        try {
            HttpEntity<ProfileDetailsDTO> request = new HttpEntity<>(target);
            ResponseEntity<String> entity = restTemplate.exchange(baseurl+"/isfollowed/"+initiator+"/"+target.getValue(), 
                HttpMethod.GET, request, String.class);
            return entity;
        }
        catch (Exception e) {
            return new ResponseEntity<String>("Error occurred while checking follow status", HttpStatus.INTERNAL_SERVER_ERROR);
        }       
    }

    @GetMapping("/getfollowed/{initiator}")
    public ResponseEntity<String> getFollowedUsers(@PathVariable String initiator) {         
        try {
            ResponseEntity<String> entity = restTemplate.getForEntity(baseurl+"/getfollowed/"+initiator, String.class);
            return entity;
        }
        catch (Exception e) {
            return new ResponseEntity<String>("Error occurred while retreiving list of followed users", HttpStatus.INTERNAL_SERVER_ERROR);
        }       
    }

    @PutMapping("/follow/{initiator}")
    public ResponseEntity<String> followUser(@PathVariable String initiator, @RequestBody ProfileDetailsDTO target) {
        try {
            HttpEntity<ProfileDetailsDTO> request = new HttpEntity<>(target);
            ResponseEntity<String> response = restTemplate.exchange(baseurl+"/follow/"+initiator, HttpMethod.PUT, request, String.class);
            return response;
        }
        catch (Exception e) {
            return new ResponseEntity<>("Error occurred while following user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/followresponse")
    public ResponseEntity<String> followResponse(@RequestBody FollowResponseDTO dto) {
        try {
            HttpEntity<FollowResponseDTO> request = new HttpEntity<>(dto);
            ResponseEntity<String> response = restTemplate.exchange(baseurl+"/followresponse", HttpMethod.PUT, request, String.class);
            return response;
        }
        catch (Exception e) {
            return new ResponseEntity<>("Error occurred while responding to users follow request", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/unfollow/{initiator}")
    public ResponseEntity<String> unfollowUser(@PathVariable String initiator, @RequestBody ProfileDetailsDTO target) {
        try {
            HttpEntity<ProfileDetailsDTO> request = new HttpEntity<>(target);
            ResponseEntity<String> response = restTemplate.exchange(baseurl+"/unfollow/"+initiator, HttpMethod.DELETE, request, String.class);
            return response;
        }
        catch (Exception e) {
            return new ResponseEntity<>("Error occurred while following user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public FollowshipCotroller() {
        this.restTemplate = new RestTemplate();
    }
}
