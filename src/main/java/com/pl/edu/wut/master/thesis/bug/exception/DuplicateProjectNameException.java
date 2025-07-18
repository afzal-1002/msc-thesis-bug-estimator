package com.pl.edu.wut.master.thesis.bug.exception;

import org.springframework.http.HttpStatus;

public class DuplicateProjectNameException extends CustomException  {
    public DuplicateProjectNameException(String message, HttpStatus status) {
        super(message, status);
    }
}
