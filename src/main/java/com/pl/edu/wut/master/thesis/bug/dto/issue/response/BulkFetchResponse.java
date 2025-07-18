package com.pl.edu.wut.master.thesis.bug.dto.issue.response;


import lombok.Data;

import java.util.List;

/**
 * Response from POST /issue/bulkfetch
 */
@Data
public class BulkFetchResponse {
    private List<IssueResponse> issues;
}

