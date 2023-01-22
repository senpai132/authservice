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

@RestController
@CrossOrigin
@RequestMapping("/notificationadapter")
public class NotificationController {
    private final String baseurl = "http://profile-service:8091/notifications";
    private RestTemplate restTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationController.class);

    @GetMapping("/getnotificationsettings/{initiator}/{target}")
    public ResponseEntity<String> getUserNotificationSettings(@PathVariable String initiator, @PathVariable String target) {
        try {
            LOGGER.info("User " + initiator + " is making a request to receive notification setting he has on user "
            + target);
            String url = baseurl+"/getnotificationsettings/" + initiator + "/" + target;
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response;
        }
        catch (Exception e) {
            LOGGER.error("Error occurred while getting user notifications settings");
            return new ResponseEntity<>("Error occurred while getting user notifications settings", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/editmsgnotification")
    public ResponseEntity<String> updateMessageNotificationSettings(@RequestBody FollowResponseDTO target) {
        try {
            LOGGER.info("User " + target.getIntiator() + " is making a request to update notification setting he has on user "
                    + target.getTarget() + " for new message creation");
            HttpEntity<FollowResponseDTO> request = new HttpEntity<>(target);
            ResponseEntity<String> response = restTemplate.exchange(baseurl+"/editmsgnotification", HttpMethod.PUT, request, String.class);
            return response;
        }
        catch (Exception e) {
            LOGGER.error("Error occurred while updating message notification settings");
            return new ResponseEntity<>("Error occurred while updating message notification settings", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/editpostnotification")
    public ResponseEntity<String> updatePostNotificationSettings(@RequestBody FollowResponseDTO target) {
        try {
            LOGGER.info("User " + target.getIntiator() + " is making a request to update notification setting he has on user "
                    + target.getTarget() + " for new post creation");
            HttpEntity<FollowResponseDTO> request = new HttpEntity<>(target);
            ResponseEntity<String> response = restTemplate.exchange(baseurl+"/editpostnotification", HttpMethod.PUT, request, String.class);
            return response;
        }
        catch (Exception e) {
            LOGGER.error("Error occurred while updateing post notification settings");
            return new ResponseEntity<>("Error occurred while updateing post notification settings", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{user}")
    public ResponseEntity<String> clearUserNotification(@PathVariable String user) {
        try {
            LOGGER.info("User " + user + " is making a request to delete all read notification");
            ResponseEntity<String> response = restTemplate.exchange(baseurl+"/" + user, HttpMethod.DELETE, null, String.class);
            return response;
        }
        catch (Exception e) {
            LOGGER.error("Error occurred while deleting user notifications");
            return new ResponseEntity<>("Error occurred while deleting user notifications", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{user}")
    public ResponseEntity<String> findAllUserNotification(@PathVariable String user) {
        try {
            LOGGER.info("User " + user + " is making a request to receive all unread notification");
            ResponseEntity<String> response = restTemplate.getForEntity(baseurl+"/" + user, String.class);
            return response;
        }
        catch (Exception e) {
            LOGGER.error("Error occurred while getting all user notifications");
            return new ResponseEntity<>("Error occurred while getting all user notifications", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public NotificationController() {
        this.restTemplate = new RestTemplate();
    }
}
