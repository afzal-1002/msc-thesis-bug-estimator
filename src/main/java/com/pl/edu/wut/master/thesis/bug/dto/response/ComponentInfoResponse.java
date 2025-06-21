package com.pl.edu.wut.master.thesis.bug.dto.response;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ComponentInfoResponse {
    private Long id;
    private String jiraComponentId;
    private String name;
    private String description;
    private UserSummaryResponse lead;
    private Long projectId;
}