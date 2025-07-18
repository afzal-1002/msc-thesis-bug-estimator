package com.pl.edu.wut.master.thesis.bug.model.jiracomment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponse {
    private String id;
    private String self;
    private String authorDisplayName;
    private OffsetDateTime created;
    private OffsetDateTime updated;
    private String issueKey;
}