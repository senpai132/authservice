package com.devops2022.DislinktAuthService.helper.dto.jobopeningsDTOs;

public class JobOpeningDTO {
    public Integer id;
    public String seniority;
    public String title;
    public String company;
    public String description;
    public String requirements;
    public String link;
    public String author;

    public JobOpeningDTO() {
    }
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getSeniority() {
        return seniority;
    }
    public void setSeniority(String seniority) {
        this.seniority = seniority;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getRequirements() {
        return requirements;
    }
    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
