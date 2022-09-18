package com.devops2022.DislinktAuthService.helper.dto.messageDTOs;

import java.util.Date;

public class MessageDTO {
    private String id;
    private String sender;
    private String receiver;
    private String content;
    private Date timeSent;
    private int isRead;

    public MessageDTO() {
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
    public String getReceiver() {
        return receiver;
    }
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Date getTimeSent() {
        return timeSent;
    }
    public void setTimeSent(Date timeSent) {
        this.timeSent = timeSent;
    }
    public int getIsRead() {
        return isRead;
    }
    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }
}
