package com.pl.edu.wut.master.thesis.bug.dto.issue.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IssueResponseSummary {
    private String id;
    private String key;
    private String self;
}
