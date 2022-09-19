package com.devops2022.DislinktAuthService.helper.dto.postDTOs;

public class LikePostDTO {
    private String username;
    private Long userPostID;
    
    public LikePostDTO() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserPostID() {
        return userPostID;
    }

    public void setUserPostID(Long userPostID) {
        this.userPostID = userPostID;
    }

    
}
