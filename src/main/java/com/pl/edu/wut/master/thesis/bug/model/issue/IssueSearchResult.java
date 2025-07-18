package com.pl.edu.wut.master.thesis.bug.model.issue;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueSearchResult {
    private String expand;
    private int startAt;
    private int maxResults;
    private int total;
    private List<IssueRecord> issues;
}