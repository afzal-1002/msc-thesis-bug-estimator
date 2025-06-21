package com.pl.edu.wut.master.thesis.bug.dto.request;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ComponentInfoRequest {
    private String jiraComponentId;
    private String name;
    private String description;
    private UserSummaryRequest lead;
    private Long projectId;
}