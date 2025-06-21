package com.pl.edu.wut.master.thesis.bug.dto.request;

import lombok.*;

import java.util.Date;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CommentRequest {
    private Long   issueId;
    private UserSummaryRequest author;
    private String body;
    private Boolean aiGenerated;
    private String jiraCommentId;

    // add these:
    private Date createdDate;
    private Date   updatedDate;
}
