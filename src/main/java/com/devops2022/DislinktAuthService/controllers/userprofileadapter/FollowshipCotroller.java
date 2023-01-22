package com.devops2022.DislinktAuthService.controllers.userprofileadapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.devops2022.DislinktAuthService.helper.dto.UserProfileDTOs.FollowResponseDTO;
import com.devops2022.DislinktAuthService.helper.dto.UserProfileDTOs.ProfileDetailsDTO;

@RestController
@CrossOrigin
@RequestMapping("/followshipadapter")
public class FollowshipCotroller {
    private RestTemplate restTemplate;
    private final String baseurl = "http://profile-service:8091/followship";
    private static final Logger LOGGER = LoggerFactory.getLogger(FollowshipCotroller.class);

    @PutMapping("/block/{initiator}")
    public ResponseEntity<String> blockUser(@PathVariable String initiator, @RequestBody ProfileDetailsDTO target) {
        try {
            LOGGER.info("User " + initiator + " is making a request to block user " + target.getValue());
            HttpEntity<ProfileDetailsDTO> request = new HttpEntity<>(target);
            restTemplate.exchange(baseurl+"/block/"+initiator, HttpMethod.PUT, request, String.class);
        }
        catch (Exception e) {
            LOGGER.error("Error occurred while blocking user");
            return new ResponseEntity<>("Error occurred while blocking user", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("User " + target.getValue() + " blocked successfully.", HttpStatus.OK);
    }

    @PutMapping("/unblock/{initiator}")
    public ResponseEntity<String> unblockUser(@PathVariable String initiator, @RequestBody ProfileDetailsDTO target) {
        try {
            LOGGER.info("User " + initiator + " is making a request to unblock user " + target.getValue());
            HttpEntity<ProfileDetailsDTO> request = new HttpEntity<>(target);
            restTemplate.exchange(baseurl+"/unblock/"+initiator, HttpMethod.PUT, request, String.class);
        }
        catch (Exception e) {
            LOGGER.error("Error occurred while unblocking user");
            return new ResponseEntity<>("Error occurred while unblocking user", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("User " + target.getValue() + " unblocked successfully.", HttpStatus.OK);
    }

    @PostMapping("/isblocked/{initiator}")
    public ResponseEntity<String> isUserBlocked(@PathVariable String initiator, @RequestBody ProfileDetailsDTO target) {         
        try {
            LOGGER.info("User " + initiator + " is making a request to check if user " + target.getValue() +
                    " is being blocked");
            HttpEntity<ProfileDetailsDTO> request = new HttpEntity<>(target);
            ResponseEntity<String> entity = restTemplate.exchange(baseurl+"/isblocked/"+initiator+"/"+target.getValue(), HttpMethod.GET, request, String.class);
            return entity;
        }
        catch (Exception e) {
            LOGGER.error("Error occurred while checking block status");
            return new ResponseEntity<String>("Error occurred while checking block status", HttpStatus.INTERNAL_SERVER_ERROR);
        }       
    }

    @PostMapping("/isfollowed/{initiator}")
    public ResponseEntity<String> isUserFollowed(@PathVariable String initiator, @RequestBody ProfileDetailsDTO target) {         
        try {
            LOGGER.info("User " + initiator + " is making a request to check if user " + target.getValue() +
                    " is being followed");
            HttpEntity<ProfileDetailsDTO> request = new HttpEntity<>(target);
            ResponseEntity<String> entity = restTemplate.exchange(baseurl+"/isfollowed/"+initiator+"/"+target.getValue(), 
                HttpMethod.GET, request, String.class);
            return entity;
        }
        catch (Exception e) {
            LOGGER.error("Error occurred while checking follow status");
            return new ResponseEntity<String>("Error occurred while checking follow status", HttpStatus.INTERNAL_SERVER_ERROR);
        }       
    }

    @GetMapping("/getfollowed/{initiator}")
    public ResponseEntity<String> getFollowedUsers(@PathVariable String initiator) {         
        try {
            LOGGER.info("User " + initiator + " is making a request to retrieve all followed users");
            ResponseEntity<String> entity = restTemplate.getForEntity(baseurl+"/getfollowed/"+initiator, String.class);
            return entity;
        }
        catch (Exception e) {
            LOGGER.error("Error occurred while retreiving list of followed users");
            return new ResponseEntity<String>("Error occurred while retreiving list of followed users", HttpStatus.INTERNAL_SERVER_ERROR);
        }       
    }

    @GetMapping("/getpending/{initiator}")
    public ResponseEntity<String> getPendingUsers(@PathVariable String initiator) {         
        try {
            LOGGER.info("User " + initiator + " is making a request to receive all pending follow requests");
            ResponseEntity<String> entity = restTemplate.getForEntity(baseurl+"/getallpending/"+initiator, String.class);
            return entity;
        }
        catch (Exception e) {
            LOGGER.error("Error occurred while retreiving list of pending follows");
            return new ResponseEntity<String>("Error occurred while retreiving list of pending follows", HttpStatus.INTERNAL_SERVER_ERROR);
        }       
    }

    @PutMapping("/follow/{initiator}")
    public ResponseEntity<String> followUser(@PathVariable String initiator, @RequestBody ProfileDetailsDTO target) {
        try {
            LOGGER.info("User " + initiator + " is making a request to follow user " + target);
            HttpEntity<ProfileDetailsDTO> request = new HttpEntity<>(target);
            ResponseEntity<String> response = restTemplate.exchange(baseurl+"/follow/"+initiator, HttpMethod.PUT, request, String.class);
            return response;
        }
        catch (Exception e) {
            LOGGER.error("Error occurred while following user");
            return new ResponseEntity<>("Error occurred while following user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/followresponse")
    public ResponseEntity<String> followResponse(@RequestBody FollowResponseDTO dto) {
        try {
            LOGGER.info("User " + dto.getIntiator() + " is responding to follow request of user " + dto.getTarget());
            HttpEntity<FollowResponseDTO> request = new HttpEntity<>(dto);
            ResponseEntity<String> response = restTemplate.exchange(baseurl+"/followresponse", HttpMethod.PUT, request, String.class);
            return response;
        }
        catch (Exception e) {
            LOGGER.error("Error occurred while responding to users follow request");
            return new ResponseEntity<>("Error occurred while responding to users follow request", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/unfollow/{initiator}")
    public ResponseEntity<String> unfollowUser(@PathVariable String initiator, @RequestBody ProfileDetailsDTO target) {
        try {
            LOGGER.info("User " + initiator + " is making a request to unfollow user " + target.getValue());
            HttpEntity<ProfileDetailsDTO> request = new HttpEntity<>(target);
            ResponseEntity<String> response = restTemplate.exchange(baseurl+"/unfollow/"+initiator, HttpMethod.DELETE, request, String.class);
            return response;
        }
        catch (Exception e) {
            LOGGER.error("Error occurred while unfollowing user");
            return new ResponseEntity<>("Error occurred while unfollowing user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public FollowshipCotroller() {
        this.restTemplate = new RestTemplate();
    }
}
