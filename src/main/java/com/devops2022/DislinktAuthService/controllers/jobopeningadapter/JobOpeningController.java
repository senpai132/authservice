package com.devops2022.DislinktAuthService.controllers.jobopeningadapter;

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

import com.devops2022.DislinktAuthService.helper.dto.jobopeningsDTOs.JobOpeningDTO;

@RestController
@CrossOrigin
@RequestMapping("/jobopeningadapter")
public class JobOpeningController {
    private RestTemplate restTemplate;
    private final String baseurl = "http://localhost:8092/jobs";
    
    
    @PostMapping("/add")
    public ResponseEntity<String> followUser(@RequestBody JobOpeningDTO dto) {
        try {
            HttpEntity<JobOpeningDTO> request = new HttpEntity<>(dto);
            ResponseEntity<String> response = restTemplate.exchange(baseurl+"/add", HttpMethod.POST, request, String.class);
            return response;
        }
        catch (Exception e) {
            return new ResponseEntity<>("Error occurred while adding new job opening", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/find/{criteria}")
    public ResponseEntity<String> findUserProfile(@PathVariable String criteria) {         
        try {        
            ResponseEntity<String> entity = restTemplate.getForEntity(baseurl+"/find/"+criteria, String.class);
            return entity;       
        } catch (Exception e) {
            return new ResponseEntity<>("Error occurred while searching for job openings", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public JobOpeningController() {
        this.restTemplate = new RestTemplate();
    }
}
