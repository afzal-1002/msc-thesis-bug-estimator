package com.pl.edu.wut.master.thesis.bug.dto.response;

import lombok.*;
import java.util.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class IssueResponse {
    private Long id;
    private String issueKey;
    private String summary;
    private String description;
    private String status;
    private String priority;
    private UserSummaryResponse assignee;
    private UserSummaryResponse reporter;
    private Long projectId;
    private Set<ComponentInfoResponse> components;
    private Set<VersionResponse> versions;
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
    private Set<CommentResponse> comments;
}