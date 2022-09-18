package com.devops2022.DislinktAuthService.helper.dto.UserProfileDTOs;

public class FollowResponseDTO {
    public String initiator;
    public String target;
    public int response;
    public int notifyOnMessage;
    public int notifyOnPost;

    public FollowResponseDTO() {

    }

    public int getNotifyOnMessage() {
        return notifyOnMessage;
    }

    public void setNotifyOnMessage(int notifyOnMessage) {
        this.notifyOnMessage = notifyOnMessage;
    }

    public int getNotifyOnPost() {
        return notifyOnPost;
    }

    public void setNotifyOnPost(int notifyOnPost) {
        this.notifyOnPost = notifyOnPost;
    }
    
    public String getIntiator() {
        return initiator;
    }

    public void setIntiator(String intiator) {
        this.initiator = intiator;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public int getResponse() {
        return response;
    }

    public void setResponse(int response) {
        this.response = response;
    }

}
