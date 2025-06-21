// com/pl/edu/wut/master/thesis/bug/mapper/IssueMapper.java
package com.pl.edu.wut.master.thesis.bug.mapper;

import com.pl.edu.wut.master.thesis.bug.dto.request.IssueRequest;
import com.pl.edu.wut.master.thesis.bug.dto.response.IssueResponse;
import com.pl.edu.wut.master.thesis.bug.enums.Priority;
import com.pl.edu.wut.master.thesis.bug.enums.Status;
import com.pl.edu.wut.master.thesis.bug.model.issue.Issue;
import com.pl.edu.wut.master.thesis.bug.model.component.ComponentInfo;
import com.pl.edu.wut.master.thesis.bug.model.version.Version;
import com.pl.edu.wut.master.thesis.bug.model.project.Project;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class IssueMapper {

    private final UserSummaryMapper   userSummaryMapper;
    private final ComponentInfoMapper componentMapper;
    private final VersionMapper       versionMapper;
    private final CommentMapper       commentMapper;


    public Issue toEntity(IssueRequest dto) {
        if (dto == null) return null;

        Project project = dto.getProjectId() != null ? Project.builder().id(dto.getProjectId()).build() : null;

        Set<ComponentInfo> comps = dto.getComponentIds() != null
                ? dto.getComponentIds().stream()
                .map(id -> ComponentInfo.builder().id(id).build())
                .collect(Collectors.toSet())
                : Collections.emptySet();

        Set<Version> versions = dto.getVersionIds() != null
                ? dto.getVersionIds().stream()
                .map(id -> Version.builder().id(id).build())
                .collect(Collectors.toSet())
                : Collections.emptySet();

        return Issue.builder()
                .id(null)
                .issueKey(dto.getIssueKey())
                .summary(dto.getSummary())
                .description(dto.getDescription())
                .status(Status.valueOf(dto.getStatus()))
                .priority(Priority.valueOf(dto.getPriority()))
                .assignee(userSummaryMapper.toEntity(dto.getAssignee()))
                .reporter(userSummaryMapper.toEntity(dto.getReporter()))
                .project(project)
                .components(comps)
                .versions(versions)
                .labels(dto.getLabels() != null ? new HashSet<>(dto.getLabels()) : Collections.emptySet())
                .timeSpentSeconds(dto.getTimeSpentSeconds())
                .originalEstimateSeconds(dto.getOriginalEstimateSeconds())
                .remainingEstimateSeconds(dto.getRemainingEstimateSeconds())
                .aggTimeSpentSeconds(dto.getAggTimeSpentSeconds())
                .aggOriginalEstimateSeconds(dto.getAggOriginalEstimateSeconds())
                .aggRemainingEstimateSeconds(dto.getAggRemainingEstimateSeconds())
                .dueDate(dto.getDueDate())
                .jiraCreatedDate(dto.getJiraCreatedDate())
                .jiraUpdatedDate(dto.getJiraUpdatedDate())
                .aiEstimation(dto.getAiEstimation())
                .aiEstimationDate(dto.getAiEstimationDate())
                .aiEstimationTotalTime(dto.getAiEstimationTotalTime())
                .build();
    }

    public IssueResponse toResponse(Issue entity) {
        if (entity == null) return null;

        IssueResponse.IssueResponseBuilder builder = IssueResponse.builder();
        builder.id(entity.getId());
        builder.issueKey(entity.getIssueKey());
        builder.summary(entity.getSummary());
        builder.description(entity.getDescription());
        builder.status(entity.getStatus().toString());
        builder.priority(entity.getPriority().toString());
        builder.assignee(userSummaryMapper.toResponse(entity.getAssignee()));
        builder.reporter(userSummaryMapper.toResponse(entity.getReporter()));
        builder.projectId(entity.getProject() != null ? entity.getProject().getId() : null);
        builder.components(entity.getComponents().stream()
                .map(componentMapper::toResponse)
                .collect(Collectors.toSet()));
        builder.versions(entity.getVersions().stream()
                .map(versionMapper::toResponse)
                .collect(Collectors.toSet()));
        builder.labels(new HashSet<>(entity.getLabels()));
        builder.timeSpentSeconds(entity.getTimeSpentSeconds());
        builder.originalEstimateSeconds(entity.getOriginalEstimateSeconds());
        builder.remainingEstimateSeconds(entity.getRemainingEstimateSeconds());
        builder.aggTimeSpentSeconds(entity.getAggTimeSpentSeconds());
        builder.aggOriginalEstimateSeconds(entity.getAggOriginalEstimateSeconds());
        builder.aggRemainingEstimateSeconds(entity.getAggRemainingEstimateSeconds());
        builder.dueDate(entity.getDueDate());
        builder.jiraCreatedDate(entity.getJiraCreatedDate());
        builder.jiraUpdatedDate(entity.getJiraUpdatedDate());
        builder.aiEstimation(entity.getAiEstimation());
        builder.aiEstimationDate(entity.getAiEstimationDate());
        builder.aiEstimationTotalTime(entity.getAiEstimationTotalTime());
        builder.comments(entity.getComments().stream().map(commentMapper::toResponse)
                .collect(Collectors.toSet()));
        return builder
                .build();
    }


    public Issue toEntity(IssueResponse dto) {
        if (dto == null) return null;

        // Basic fields
        Issue.IssueBuilder builder = Issue.builder();
        builder.id(dto.getId());
        builder.issueKey(dto.getIssueKey());
        builder.summary(dto.getSummary());
        builder.description(dto.getDescription());
        builder.status(Status.valueOf(dto.getStatus()));
        builder.priority(Priority.valueOf(dto.getPriority()));
        builder.timeSpentSeconds(dto.getTimeSpentSeconds());
        builder.originalEstimateSeconds(dto.getOriginalEstimateSeconds());
        builder.remainingEstimateSeconds(dto.getRemainingEstimateSeconds());
        builder.aggTimeSpentSeconds(dto.getAggTimeSpentSeconds());
        builder.aggOriginalEstimateSeconds(dto.getAggOriginalEstimateSeconds());
        builder.aggRemainingEstimateSeconds(dto.getAggRemainingEstimateSeconds());
        builder.dueDate(dto.getDueDate());
        builder.jiraCreatedDate(dto.getJiraCreatedDate());
        builder.jiraUpdatedDate(dto.getJiraUpdatedDate());
        builder.aiEstimation(dto.getAiEstimation());
        builder.aiEstimationDate(dto.getAiEstimationDate());
        builder.aiEstimationTotalTime(dto.getAiEstimationTotalTime());// Time tracking fields
// Date fields
// AI estimation
        Issue issue = builder.build();

        // Embed assignee & reporter
        issue.setAssignee(dto.getAssignee() != null ? userSummaryMapper.toEntity(dto.getAssignee()) : null);
        issue.setReporter(dto.getReporter() != null ? userSummaryMapper.toEntity(dto.getReporter()) : null);

        // Link project stub
        if (dto.getProjectId() != null) {
            issue.setProject(Project.builder().id(dto.getProjectId()).build());
        }

        // Map components by ID
        if (dto.getComponents() != null) {
            Set<ComponentInfo> comps = dto.getComponents().stream()
                    .map(resp -> ComponentInfo.builder().id(resp.getId()).build())
                    .collect(Collectors.toSet());
            issue.setComponents(comps);
        }

        // Map versions by ID
        if (dto.getVersions() != null) {
            Set<Version> versions = dto.getVersions().stream()
                    .map(resp -> Version.builder().id(resp.getId()).build())
                    .collect(Collectors.toSet());
            issue.setVersions(versions);
        }

        // Labels
        if (dto.getLabels() != null) {
            issue.setLabels(new HashSet<>(dto.getLabels()));
        }

        // Comments are handled separately (Optionally you can map them here)
         issue.setComments(dto.getComments().stream()
             .map(commentMapper::toEntity)
             .collect(Collectors.toSet()));

        return issue;
    }


}
