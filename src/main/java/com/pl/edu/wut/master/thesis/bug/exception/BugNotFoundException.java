package com.pl.edu.wut.master.thesis.bug.exception;


import org.springframework.http.HttpStatus;

public class BugNotFoundException extends CustomException {

    public BugNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
