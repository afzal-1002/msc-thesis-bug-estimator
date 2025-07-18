package com.pl.edu.wut.master.thesis.bug.dto.project;

import lombok.Data;

@Data
public class CreateProjectRequest {
    private String key;
    private String name;
    private String projectTypeKey;
    private String projectTemplateKey;
    private String leadAccountId;
    private String description;
}