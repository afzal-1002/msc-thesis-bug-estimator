package com.pl.edu.wut.master.thesis.bug.dto.request;

import lombok.Data;

@Data
public class JiraFetchProjectRequest {
        private String jiraBaseUrl;
        private String jiraUsername;
        private String jiraToken;
        private String projectKeyOrId;
}