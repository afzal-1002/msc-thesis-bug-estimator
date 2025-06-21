package com.pl.edu.wut.master.thesis.bug.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectRequest {
    private String jiraId;
    private String jiraUsername;
    private String userToken;
    private String baseUrl;
}
