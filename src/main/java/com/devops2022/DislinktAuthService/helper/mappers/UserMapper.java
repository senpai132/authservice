package com.devops2022.DislinktAuthService.helper.mappers;

import java.util.ArrayList;
import java.util.List;

import com.devops2022.DislinktAuthService.helper.dto.UserDTO;
import com.devops2022.DislinktAuthService.model.User;

public class UserMapper implements MapperInterface<User, UserDTO>{
    @Override
    public User toEntity(UserDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        return user;
    }

    @Override
    public List<User> toEntityList(List<UserDTO> dtoList) {
        List<User> users = new ArrayList<>();
        for(UserDTO dto : dtoList){
            users.add(toEntity(dto));
        }
        return users;
    }

    @Override
    public UserDTO toDto(User entity) {
        UserDTO dto = new UserDTO();
        dto.setUsername(entity.getUsername());
        return dto;
    }

    @Override
    public List<UserDTO> toDtoList(List<User> entityList) {
        List<UserDTO> userDTOS = new ArrayList<>();
        for(User user : entityList){
            userDTOS.add(toDto(user));
        }
        return userDTOS;
    }
}