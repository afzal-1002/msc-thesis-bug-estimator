package com.pl.edu.wut.master.thesis.bug.dto.issue.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class BulkFetchRequest {
    private List<String> expand;
    private List<String> fields;
    private boolean fieldsByKeys;
    @JsonProperty("issueIdsOrKeys")
    private List<String> issueIdsOrKeys;
    private List<String> properties;
}
