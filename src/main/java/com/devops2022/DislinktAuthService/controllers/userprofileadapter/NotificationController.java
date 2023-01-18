package com.devops2022.DislinktAuthService.controllers.userprofileadapter;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.devops2022.DislinktAuthService.helper.dto.UserProfileDTOs.FollowResponseDTO;

@RestController
@CrossOrigin
@RequestMapping("/notificationadapter")
public class NotificationController {
    private final String baseurl = "http://profile-service:8091/notifications";
    private RestTemplate restTemplate;

    @GetMapping("/getnotificationsettings/{initiator}/{target}")
    public ResponseEntity<String> getUserNotificationSettings(@PathVariable String initiator, @PathVariable String target) {
        try {
            String url = baseurl+"/getnotificationsettings/" + initiator + "/" + target;
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response;
        }
        catch (Exception e) {
            return new ResponseEntity<>("Error occurred while getting user notifications settings", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/editmsgnotification")
    public ResponseEntity<String> updateMessageNotificationSettings(@RequestBody FollowResponseDTO target) {
        try {
            HttpEntity<FollowResponseDTO> request = new HttpEntity<>(target);
            ResponseEntity<String> response = restTemplate.exchange(baseurl+"/editmsgnotification", HttpMethod.PUT, request, String.class);
            return response;
        }
        catch (Exception e) {
            return new ResponseEntity<>("Error occurred while updating message notification settings", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/editpostnotification")
    public ResponseEntity<String> updatePostNotificationSettings(@RequestBody FollowResponseDTO target) {
        try {
            HttpEntity<FollowResponseDTO> request = new HttpEntity<>(target);
            ResponseEntity<String> response = restTemplate.exchange(baseurl+"/editpostnotification", HttpMethod.PUT, request, String.class);
            return response;
        }
        catch (Exception e) {
            return new ResponseEntity<>("Error occurred while updateing post notification settings", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{user}")
    public ResponseEntity<String> clearUserNotification(@PathVariable String user) {
        try {
            ResponseEntity<String> response = restTemplate.exchange(baseurl+"/" + user, HttpMethod.DELETE, null, String.class);
            return response;
        }
        catch (Exception e) {
            return new ResponseEntity<>("Error occurred while deleting user notifications", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{user}")
    public ResponseEntity<String> findAllUserNotification(@PathVariable String user) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(baseurl+"/" + user, String.class);
            return response;
        }
        catch (Exception e) {
            return new ResponseEntity<>("Error occurred while getting all user notifications", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public NotificationController() {
        this.restTemplate = new RestTemplate();
    }
}
