package com.pl.edu.wut.master.thesis.bug.dto.comment;

import com.pl.edu.wut.master.thesis.bug.dto.issue.response.IssueSummaryDto; // Import the IssueSummaryDto
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseWithIssueSummary {
    private String id;
    private String self;
    private String authorDisplayName;
    private OffsetDateTime created;
    private OffsetDateTime updated;
    private String issueKey;
    private IssueSummaryDto issueSummary;
}
