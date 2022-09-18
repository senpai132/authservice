package com.devops2022.DislinktAuthService.controllers.userprofileadapter;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.devops2022.DislinktAuthService.helper.dto.UserProfileDTOs.FollowResponseDTO;

@RestController
@CrossOrigin
@RequestMapping("/notificationadapter")
public class NotificationController {
    private final String baseurl = "http://localhost:8091/notifications";
    private RestTemplate restTemplate;

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

    public NotificationController() {
        this.restTemplate = new RestTemplate();
    }
}
