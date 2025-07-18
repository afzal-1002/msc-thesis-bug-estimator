package com.pl.edu.wut.master.thesis.bug.exception.jiraexception;

import com.pl.edu.wut.master.thesis.bug.exception.CustomException;
import org.springframework.http.HttpStatus;

public class JiraIntegrationException extends CustomException {

    public JiraIntegrationException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
