package com.pl.edu.wut.master.thesis.bug.dto.response;

import com.pl.edu.wut.master.thesis.bug.dto.request.UserSummaryRequest;
import lombok.*;
import java.util.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CommentResponse {
    private Long issueId;
    private UserSummaryResponse author;
    private String body;
    private Date createdDate;
    private Date updatedDate;
    private Boolean aiGenerated;
    private String jiraCommentId;
}

