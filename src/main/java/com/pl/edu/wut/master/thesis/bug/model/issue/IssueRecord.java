package com.pl.edu.wut.master.thesis.bug.model.issue;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueRecord {
    private String expand;
    private String id;
    private String self;
    private String key;
    private IssueFields fields;
}