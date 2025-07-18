package com.pl.edu.wut.master.thesis.bug.model.issuetype;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class CreateIssueTypeResponse {

    private String    self;
    @Id
    private Long    id;
    @Column(columnDefinition = "TEXT")
    private String    description;
    private String    iconUrl;
    @Column(nullable = false)
    private String    name;
    private String    untranslatedName;
    private boolean   subtask;
    private Long      avatarId;
    private Integer   hierarchyLevel;
    private Scope scope;

    @Data
    public static class Scope {
        private String        type;
        private Project       project;
    }

    @Data
    public static class Project {
        private String        id;
        private String        key;
        private String        name;
    }
}