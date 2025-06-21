package com.pl.edu.wut.master.thesis.bug.dto.response;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class VersionResponse {
    private Long id;
    private String jiraVersionId;
    private String name;
    private String description;
    private Long projectId;
}