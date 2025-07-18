package com.pl.edu.wut.master.thesis.bug.dto.issue.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class BulkIssueRequest {
    @JsonProperty("issueUpdates")
    private List<BulkIssueUpdate> issueUpdates;

    @Data
    public static class BulkIssueUpdate {
        /** The full “fields” block */
        private Map<String, Object> fields;
        /** The full “update” block */
        private Map<String, Object> update;
    }
}