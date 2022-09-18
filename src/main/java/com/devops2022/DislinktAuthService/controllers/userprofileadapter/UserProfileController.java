package com.devops2022.DislinktAuthService.controllers.userprofileadapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.devops2022.DislinktAuthService.helper.dto.UserDTO;
import com.devops2022.DislinktAuthService.helper.dto.UserProfileDTOs.ProfileDetailsDTO;
import com.devops2022.DislinktAuthService.helper.dto.UserProfileDTOs.UserProfileDTO;
import com.devops2022.DislinktAuthService.helper.mappers.UserMapper;
import com.devops2022.DislinktAuthService.services.ClientService;

@RestController
@CrossOrigin
@RequestMapping("/userprofileadapter")
public class UserProfileController {
    private RestTemplate restTemplate;
    private UserMapper userMapper;

    @Autowired
    ClientService clientService;

    private final String baseurl = "http://localhost:8091/userprofile";

    @GetMapping("/find/{searchCriteria}")
    public ResponseEntity<String> findUserProfile(@PathVariable String searchCriteria) {         
        ResponseEntity<String> entity = restTemplate.getForEntity(baseurl+"/find/"+searchCriteria, String.class);

        //restTemplate.exchange(baseurl+"/find/"+searchCriteria, HttpMethod.GET, entity, UserProfileDTO.class).getBody();
        return entity;       
    }

    @PutMapping("/edit/{username}")
    public ResponseEntity<String> editUserProfile(@PathVariable String username, @RequestBody UserProfileDTO userProfileDTO) {
        try {
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(userProfileDTO.getUsername());
            clientService.editUser(username, userMapper.toEntity(userDTO));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        HttpEntity<UserProfileDTO> request = new HttpEntity<>(userProfileDTO);
        restTemplate.exchange(baseurl+"/edit/"+username, HttpMethod.PUT, request, String.class);

        return new ResponseEntity<>("User edited successfully", HttpStatus.OK);
    }

    @PutMapping("/editvisibility/{username}")
    public ResponseEntity<String> editUserVisibility(@PathVariable String username, @RequestBody ProfileDetailsDTO userProfileDTO) {

        HttpEntity<ProfileDetailsDTO> request = new HttpEntity<>(userProfileDTO);
        restTemplate.exchange(baseurl+"/editvisibility/"+username, HttpMethod.PUT, request, String.class);

        return new ResponseEntity<>("Users visibility edited successfully", HttpStatus.OK);
    }

    @PutMapping("/editexperience/{username}")
    public ResponseEntity<String> editUserExperience(@PathVariable String username, @RequestBody ProfileDetailsDTO userProfileDTO) {

        HttpEntity<ProfileDetailsDTO> request = new HttpEntity<>(userProfileDTO);
        restTemplate.exchange(baseurl+"/editexperience/"+username, HttpMethod.PUT, request, String.class);

        return new ResponseEntity<>("Users experience edited successfully", HttpStatus.OK);
    }

    @PutMapping("/editeducation/{username}")
    public ResponseEntity<String> editUserEducation(@PathVariable String username, @RequestBody ProfileDetailsDTO userProfileDTO) {

        HttpEntity<ProfileDetailsDTO> request = new HttpEntity<>(userProfileDTO);
        restTemplate.exchange(baseurl+"/editeducation/"+username, HttpMethod.PUT, request, String.class);

        return new ResponseEntity<>("Users education edited successfully", HttpStatus.OK);
    }

    @PutMapping("/editskills/{username}")
    public ResponseEntity<String> editUserSkills(@PathVariable String username, @RequestBody ProfileDetailsDTO userProfileDTO) {

        HttpEntity<ProfileDetailsDTO> request = new HttpEntity<>(userProfileDTO);
        restTemplate.exchange(baseurl+"/editskills/"+username, HttpMethod.PUT, request, String.class);

        return new ResponseEntity<>("Users skills edited successfully", HttpStatus.OK);
    }

    @PutMapping("/edithobbies/{username}")
    public ResponseEntity<String> editUserHobbies(@PathVariable String username, @RequestBody ProfileDetailsDTO userProfileDTO) {

        HttpEntity<ProfileDetailsDTO> request = new HttpEntity<>(userProfileDTO);
        restTemplate.exchange(baseurl+"/edithobbies/"+username, HttpMethod.PUT, request, String.class);

        return new ResponseEntity<>("Users hobbies edited successfully", HttpStatus.OK);
    }

    public UserProfileController() {
        this.restTemplate = new RestTemplate();
        this.userMapper = new UserMapper();
    }
}
