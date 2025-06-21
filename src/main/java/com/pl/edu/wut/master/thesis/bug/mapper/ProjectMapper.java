package com.pl.edu.wut.master.thesis.bug.mapper;

import com.pl.edu.wut.master.thesis.bug.dto.response.ProjectCategoryResponse;
import com.pl.edu.wut.master.thesis.bug.dto.response.ProjectResponse;
import com.pl.edu.wut.master.thesis.bug.dto.response.UserSummaryResponse;
import com.pl.edu.wut.master.thesis.bug.model.project.Project;
import com.pl.edu.wut.master.thesis.bug.model.project.ProjectCategory;
import com.pl.edu.wut.master.thesis.bug.model.user.User;
import com.pl.edu.wut.master.thesis.bug.model.user.UserSummary;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Component
@AllArgsConstructor
public class ProjectMapper {
    private  UserMapper userMapper;

    public ProjectResponse toResponse(Project project) {
        if (project == null) {
            return null;
        }

        return ProjectResponse.builder()
                .id(project.getId())
                .jiraId(project.getJiraId())
                .key(project.getKey())
                .name(project.getName())
                .description(project.getDescription())
                .users(
                        project.getUsers().stream()
                                .map(UserMapper::mapToResponse)
                                .collect(Collectors.toSet())
                )
                .lead(toUserSummaryResponse(project.getLead()))
                .category(toProjectCategoryResponse(project.getCategory()))
                .roles(project.getRoles())
                .projectTypeKey(project.getProjectTypeKey())
                .baseUrl(project.getBaseUrl())
                .jiraUserName(project.getJiraUsername())
                .jiraUserToken(project.getUserToken())
                .build();
    }

    private UserSummaryResponse toUserSummaryResponse(UserSummary userSummary) {
        if (userSummary == null) {
            return null;
        }
        return new UserSummaryResponse(
                userSummary.getAccountId(),
                userSummary.getEmailAddress(),
                userSummary.getDisplayName()
        );
    }

    private ProjectCategoryResponse toProjectCategoryResponse(ProjectCategory category) {
        if (category == null) {
            return null;
        }
        return new ProjectCategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }
}