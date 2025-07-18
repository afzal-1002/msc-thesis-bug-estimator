package com.pl.edu.wut.master.thesis.bug.model.issue;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IssueSummary {
    private String id;
    private String key;
    private String summary;
    private String description;
    private String status;
    private String issueType;
    private String projectKey;
}

