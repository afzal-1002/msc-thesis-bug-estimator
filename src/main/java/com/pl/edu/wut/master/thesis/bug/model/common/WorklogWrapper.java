package com.pl.edu.wut.master.thesis.bug.model.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorklogWrapper {
    private int           startAt;
    private int           maxResults;
    private int           total;
    private List<Worklog> worklogs;
}
