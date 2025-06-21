package com.pl.edu.wut.master.thesis.bug.dto.request;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class VersionRequest {
    private String jiraVersionId;
    private String name;
    private String description;
    private Long projectId;
}