package com.pl.edu.wut.master.thesis.bug.dto.issue.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ArchiveRequest {
    @JsonProperty("issueIdsOrKeys")
    private List<String> issueIdsOrKeys;
}
