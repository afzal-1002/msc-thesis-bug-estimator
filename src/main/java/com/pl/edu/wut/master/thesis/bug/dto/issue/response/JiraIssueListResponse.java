package com.pl.edu.wut.master.thesis.bug.dto.issue.response;

import lombok.Data;
import java.util.List;

@Data
public class JiraIssueListResponse {
    private List<IssueResponse> issues;
}