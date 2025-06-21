package com.pl.edu.wut.master.thesis.bug.exception.jiraexception;


import com.pl.edu.wut.master.thesis.bug.exception.CustomException;
import org.springframework.http.HttpStatus;

public class JiraAuthenticationException extends CustomException {
    public JiraAuthenticationException(String reason) {
        super(reason, HttpStatus.UNAUTHORIZED);
    }
}