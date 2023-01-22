package com.devops2022.DislinktAuthService.controllers.jobopeningadapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final String baseurl = "http://job-service:8092/jobs";
    private static final Logger LOGGER = LoggerFactory.getLogger(JobOpeningController.class);
    
    @PostMapping("/add")
    public ResponseEntity<String> followUser(@RequestBody JobOpeningDTO dto) {
        try {
            LOGGER.info("User " + dto.getAuthor() + "is creating new job offer for company " + dto.getCompany()
            + " with title " + dto.getTitle());
            HttpEntity<JobOpeningDTO> request = new HttpEntity<>(dto);
            ResponseEntity<String> response = restTemplate.exchange(baseurl+"/add", HttpMethod.POST, request, String.class);
            return response;
        }
        catch (Exception e) {
            LOGGER.error("Error occurred while adding new job opening");
            return new ResponseEntity<>("Error occurred while adding new job opening", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/find/{criteria}")
    public ResponseEntity<String> findUserProfile(@PathVariable String criteria) {         
        try {
            LOGGER.info("Searching for job offer that fits criteria " + criteria);
            ResponseEntity<String> entity = restTemplate.getForEntity(baseurl+"/find/"+criteria, String.class);
            return entity;       
        } catch (Exception e) {
            LOGGER.error("Error occurred while searching for job openings");
            return new ResponseEntity<>("Error occurred while searching for job openings", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public JobOpeningController() {
        this.restTemplate = new RestTemplate();
    }
}
