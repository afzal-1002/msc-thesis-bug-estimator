package com.pl.edu.wut.master.thesis.bug.exception.jiraexception;

import com.pl.edu.wut.master.thesis.bug.exception.CustomException;
import org.springframework.http.HttpStatus;



public class JiraProjectNotFoundException extends CustomException {
    public JiraProjectNotFoundException(String projectKey) {
        super("Project not found in Jira: " + projectKey, HttpStatus.NOT_FOUND);
    }
}