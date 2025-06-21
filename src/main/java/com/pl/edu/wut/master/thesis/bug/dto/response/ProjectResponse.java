package com.pl.edu.wut.master.thesis.bug.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.Map;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponse {
    private String jiraId;
    private String key;
    private String name;
    private String description;
    private UserSummaryResponse lead;
    private ProjectCategoryResponse category;
    private Map<String, String> roles;
    private String projectTypeKey;
    private String baseUrl;
    private Long id;

    private Set<UserSummaryResponse> users;

    @JsonIgnore
    private String jiraUserName;

    @JsonIgnore
    private String jiraUserToken;
}
