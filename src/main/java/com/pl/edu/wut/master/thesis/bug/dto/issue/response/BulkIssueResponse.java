package com.pl.edu.wut.master.thesis.bug.dto.issue.response;


import lombok.Data;

import java.util.List;

@Data
public class BulkIssueResponse {
    /** Array of created/updated issues */
    private List<IssueResponse> issues;
}
