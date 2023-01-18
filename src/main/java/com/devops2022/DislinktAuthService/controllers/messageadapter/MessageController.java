package com.devops2022.DislinktAuthService.controllers.messageadapter;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.devops2022.DislinktAuthService.helper.dto.messageDTOs.MessageDTO;

@RestController
@CrossOrigin
@RequestMapping("/messageadapter")
public class MessageController {
    private RestTemplate restTemplate;
    private final String baseurl = "http://message-service:8093/msg";
    private final String notificationBaseUrl = "http://profile-service:8091";

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody MessageDTO dto) {
        try {
            HttpEntity<MessageDTO> request = new HttpEntity<>(dto);
            ResponseEntity<String> responseEntity = restTemplate.exchange(baseurl+"/send/", HttpMethod.POST, request, String.class);
            ResponseEntity<String> entity = restTemplate.exchange(
                    notificationBaseUrl + "/notifications/new-msg-notification/" + dto.getSender() + "/" + dto.getReceiver(),
                    HttpMethod.POST, null, String.class);
            return  responseEntity;
        }
        catch (Exception e) {
            return new ResponseEntity<>("Error occurred while sending a message", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getbysender/{username}")
    public ResponseEntity<String> getMessagesBySender(@PathVariable String username) {         
        try {
            ResponseEntity<String> entity = restTemplate.getForEntity(baseurl+"/getbysender/"+username, String.class);
            return entity;       
    
        } catch (Exception e) {
            return new ResponseEntity<String>("Error occurred while retreiving messages", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getbyreceiver/{username}")
    public ResponseEntity<String> getMessageByReceiver(@PathVariable String username) {         
        try {
            ResponseEntity<String> entity = restTemplate.getForEntity(baseurl+"/getbyreceiver/"+username, String.class);
            return entity;       
    
        } catch (Exception e) {
            return new ResponseEntity<String>("Error occurred while retreiving messages", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("readmsg/{id}")
    public ResponseEntity<String> readMsg(@PathVariable String id) {
        try {
            //HttpEntity<ProfileDetailsDTO> request = new HttpEntity<>(target);
            return restTemplate.exchange(baseurl+"/readmsg/"+id, HttpMethod.PUT, null, String.class);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Error occurred while reading a message", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getunreadmsgs/{username}")
    public ResponseEntity<String> getUnreadMessages(@PathVariable String username) {         
        try {
            ResponseEntity<String> entity = restTemplate.getForEntity(baseurl+"/getunreadmsgs/"+username, String.class);
            return entity;       
    
        } catch (Exception e) {
            return new ResponseEntity<String>("Error occurred while retreiving unread messages", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getconversation/{participantone}/{participanttwo}")
    public ResponseEntity<String> getConversation(@PathVariable String participantone, @PathVariable String participanttwo) {         
        try {
            ResponseEntity<String> entity = restTemplate.getForEntity(baseurl+"/getconversation/"+participantone+"/"+participanttwo, 
                String.class);
            return entity;       
    
        } catch (Exception e) {
            return new ResponseEntity<String>("Error occurred while retreiving conversation", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public MessageController() {
        this.restTemplate = new RestTemplate();
    }
    
}
