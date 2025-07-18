package com.pl.edu.wut.master.thesis.bug.model.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pl.edu.wut.master.thesis.bug.model.user.UserSummary;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Worklog {
    private UserSummary author;
    private Description comment;
    private String       id;
    private String       issueId;
    private String       self;
    private OffsetDateTime started;
    private String       timeSpent;
    private int          timeSpentSeconds;
    private UserSummary updateAuthor;
    private OffsetDateTime updated;
}
