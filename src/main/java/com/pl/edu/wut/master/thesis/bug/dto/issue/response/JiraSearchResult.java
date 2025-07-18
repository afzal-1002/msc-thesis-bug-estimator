package com.pl.edu.wut.master.thesis.bug.dto.issue.response;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class JiraSearchResult {
    private int startAt;
    private int maxResults;
    private int total;
    private List<IssueResponse> issues;
}
