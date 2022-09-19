package com.devops2022.DislinktAuthService.helper.dto.postDTOs;

import java.util.Date;

public class CommentDTO {

    private String commentText;
    private String username;
    private Long userPostId;
    private Date commentDate;

    public CommentDTO() {

    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserPostId() {
        return userPostId;
    }

    public void setUserPostId(Long userPostId) {
        this.userPostId = userPostId;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    
    
}
