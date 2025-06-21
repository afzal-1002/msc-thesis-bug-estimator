package com.pl.edu.wut.master.thesis.bug.dto.request;

import lombok.*;
import java.util.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class IssueRequest {
    private String issueKey;
    private String summary;
    private String description;
    private String status;
    private String priority;
    private UserSummaryRequest assignee;
    private UserSummaryRequest reporter;
    private Long projectId;
    private Set<Long> componentIds;
    private Set<Long> versionIds;
    private Set<String> labels;
    private Integer timeSpentSeconds;
    private Integer originalEstimateSeconds;
    private Integer remainingEstimateSeconds;
    private Integer aggTimeSpentSeconds;
    private Integer aggOriginalEstimateSeconds;
    private Integer aggRemainingEstimateSeconds;
    private Date dueDate;
    private Date jiraCreatedDate;
    private Date jiraUpdatedDate;
    private String aiEstimation;
    private Date aiEstimationDate;
    private Integer aiEstimationTotalTime;
}