package com.pl.edu.wut.master.thesis.bug.model.jira;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JiraCredentials {
    private String jiraBaseUrl;
    private String jiraUsername;
    private String jiraToken;
}