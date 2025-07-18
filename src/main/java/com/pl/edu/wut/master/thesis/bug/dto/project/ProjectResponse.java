package com.pl.edu.wut.master.thesis.bug.dto.project;

import com.pl.edu.wut.master.thesis.bug.dto.issue.response.IssueResponseSummary;
import com.pl.edu.wut.master.thesis.bug.model.user.UserSummary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponse {
    private Long id;
    private String key;
    private String name;
    private String description;
    private String baseUrl;
    private String projectTypeKey;
    private UserSummary lead;
    private List<UserSummary> users;
    private List<IssueResponseSummary> issues;
}