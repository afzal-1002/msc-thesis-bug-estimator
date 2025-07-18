package com.pl.edu.wut.master.thesis.bug.mapper;

import com.pl.edu.wut.master.thesis.bug.dto.issue.response.IssueResponse;
import com.pl.edu.wut.master.thesis.bug.enums.PriorityEnum;
import com.pl.edu.wut.master.thesis.bug.enums.Status;
import com.pl.edu.wut.master.thesis.bug.enums.SynchronizationStatus;
import com.pl.edu.wut.master.thesis.bug.model.common.Description;
import com.pl.edu.wut.master.thesis.bug.model.common.Priority;
import com.pl.edu.wut.master.thesis.bug.model.common.StatusDetails;
import com.pl.edu.wut.master.thesis.bug.model.issue.Issue;
import com.pl.edu.wut.master.thesis.bug.model.issue.IssueFields;
import com.pl.edu.wut.master.thesis.bug.model.issue.IssueRecord;
import com.pl.edu.wut.master.thesis.bug.model.issuetype.IssueType;
import com.pl.edu.wut.master.thesis.bug.model.project.Project;
import com.pl.edu.wut.master.thesis.bug.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class IssueMapper {

    private final ProjectService projectService;

    public Issue toIssueEntity(IssueResponse issueResponse, Project project, IssueType issueType) {
        if (issueResponse == null || issueResponse.getFields() == null) {
            return null;
        }
        IssueFields fields = issueResponse.getFields();
        Issue.IssueBuilder issueBuilder = baseBuilder(
                issueResponse.getId(),
                issueResponse.getKey(),
                issueResponse.getSelf(),
                fields
        );
        issueBuilder
                .issueType(issueType)
                .project(project)
                .syncStatus(SynchronizationStatus.SYNCED);
        return issueBuilder.build();
    }

    public Issue toIssueEntity(IssueRecord issueRecord) {
        if (issueRecord == null) { return null; }

        IssueFields fields = issueRecord.getFields();
        Issue.IssueBuilder issueBuilder = baseBuilder( issueRecord.getId(),
                issueRecord.getKey(), issueRecord.getSelf(), fields);

        if (fields.getIssuetype() != null) {
            IssueType issueType = IssueType.builder()
                    .id(fields.getIssuetype().getId())
                    .name(fields.getIssuetype().getName())
                    .description(fields.getIssuetype().getDescription())
                    .iconUrl(fields.getIssuetype().getIconUrl())
                    .subtask(fields.getIssuetype().isSubtask())
                    .avatarId(fields.getIssuetype().getAvatarId())
                    .hierarchyLevel(fields.getIssuetype().getHierarchyLevel())
                    .build();
            issueBuilder.issueType(issueType);
        }
        if (fields.getProject() != null && fields.getProject().getId() != null) {
            Optional<Project> optionalProject = projectService.findById(
                    Long.valueOf(fields.getProject().getId())
            );
            optionalProject.ifPresent(issueBuilder::project);
        }
        return issueBuilder.build();
    }

    public Issue toIssueEntity(IssueRecord issueRecord, Project project) {
        if (issueRecord == null) { return null; }

        IssueFields fields = issueRecord.getFields();
        Issue.IssueBuilder issueBuilder = baseBuilder(
                issueRecord.getId(),
                issueRecord.getKey(),
                issueRecord.getSelf(),
                fields
        );
        if (fields.getIssuetype() != null) {
            IssueType issueType = IssueType.builder()
                    .id(fields.getIssuetype().getId())
                    .name(fields.getIssuetype().getName())
                    .description(fields.getIssuetype().getDescription())
                    .iconUrl(fields.getIssuetype().getIconUrl())
                    .subtask(fields.getIssuetype().isSubtask())
                    .avatarId(fields.getIssuetype().getAvatarId())
                    .hierarchyLevel(fields.getIssuetype().getHierarchyLevel())
                    .build();
            issueBuilder.issueType(issueType);
        }
        issueBuilder.project(project).syncStatus(SynchronizationStatus.SYNCED);
        return issueBuilder.build();
    }

    public Issue toIssueEntity(IssueFields fields) {
        if (fields == null) { return null; }
        Issue.IssueBuilder issueBuilder = baseBuilder(null, null,
                null, fields);
        issueBuilder.syncStatus(SynchronizationStatus.SYNCED);
        return issueBuilder.build();
    }

    public Issue toIssueEntity(IssueResponse response) {
        if (response == null || response.getFields() == null) {
            return null;
        }
        Issue issue = toIssueEntity(response.getFields());
        if (issue != null) {
            issue.setId(response.getId() != null ? Long.parseLong(response.getId()) : null);
            issue.setKey(response.getKey());
            issue.setSelf(response.getSelf());
        }
        return issue;
    }

    public IssueResponse toIssueResponse(Issue issueEntity) {
        if (issueEntity == null) {
            return null;
        }
        IssueFields fields = new IssueFields();
        fields.setSummary(issueEntity.getSummary());
        fields.setDescription(null);
        StatusDetails statusDetails = null;
        if (issueEntity.getStatus() != null) {
            statusDetails = new StatusDetails();
            statusDetails.setName(String.valueOf(issueEntity.getStatus()));
        }
        fields.setStatus(statusDetails);
        fields.setPriority(convertEnumToPriorityDto(issueEntity.getPriority()));
        fields.setEnvironment(issueEntity.getEnvironment());
        fields.setParent(fields.getParent());
        fields.setLabels(issueEntity.getLabels() != null
                ? new ArrayList<>(issueEntity.getLabels())
                : Collections.emptyList()
        );
        fields.setTimetracking(issueEntity.getTimeTracking());
        fields.setAssignee(issueEntity.getAssignee());
        fields.setReporter(issueEntity.getReporter());
        fields.setCreated(issueEntity.getCreatedAt() != null
                ? issueEntity.getCreatedAt().atOffset(OffsetDateTime.now().getOffset())
                : null
        );
        fields.setUpdated(issueEntity.getUpdatedAt() != null
                ? issueEntity.getUpdatedAt().atOffset(OffsetDateTime.now().getOffset())
                : null
        );
        fields.setResolutionDate(issueEntity.getResolvedAt() != null
                ? issueEntity.getResolvedAt().atOffset(OffsetDateTime.now().getOffset())
                : null
        );
        fields.setDuedate(issueEntity.getDueDate());
        IssueResponse.IssueResponseBuilder builder = IssueResponse.builder();
        builder.id(issueEntity.getId() != null ? issueEntity.getId().toString() : null);
        builder.key(issueEntity.getKey());
        builder.self(issueEntity.getSelf());
        builder.fields(fields);
        return builder.build();
    }

    private Issue.IssueBuilder baseBuilder(
            String idString,
            String issueKey,
            String selfUrl,
            IssueFields fields
    ) {
        Issue.IssueBuilder builder = Issue.builder();
        builder.id(idString != null ? Long.parseLong(idString) : null);
        builder.key(issueKey);
        builder.self(selfUrl);
        builder.summary(fields.getSummary());
        builder.description(fields.getDescription() != null
                ? flattenDescription(fields.getDescription())
                : null
        );
        builder.status(fields.getStatus() != null
                ? Status.valueOf(fields.getStatus().getName())
                : null
        );
        builder.priority(convertPriorityDtoToEnum(fields.getPriority()));
        builder.environment(fields.getEnvironment());
        builder.parentKey(fields.getParent() != null
                ? fields.getParent().getKey()
                : null
        );
        builder.labels(fields.getLabels() != null
                ? new ArrayList<>(fields.getLabels())
                : Collections.emptyList()
        );
        builder.timeTracking(fields.getTimetracking());
        builder.createdAt(convertToLocalDateTime(fields.getCreated()));
        builder.updatedAt(convertToLocalDateTime(fields.getUpdated()));
        builder.resolvedAt(convertToLocalDateTime(fields.getResolutionDate()));
        builder.dueDate(fields.getDuedate());
        builder.assignee(fields.getAssignee());
        builder.reporter(fields.getReporter());
        return builder;
    }

    private static String flattenDescription(Description description) {
        if (description == null || description.getContent() == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        description.getContent().forEach(block -> {
            if (block.getContent() != null) {
                block.getContent().forEach(item -> {
                    if (item.getText() != null) {
                        sb.append(item.getText()).append("\n");
                    }
                });
            }
        });
        return sb.toString().trim();
    }

    private static LocalDateTime convertToLocalDateTime(OffsetDateTime odt) {
        return odt != null ? odt.toLocalDateTime() : null;
    }

    private static PriorityEnum convertPriorityDtoToEnum(Priority priority) {
        if (priority == null || priority.getName() == null) {
            return null;
        }
        try {
            return PriorityEnum.valueOf(priority.getName().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private static Priority convertEnumToPriorityDto(PriorityEnum priorityEnum) {
        if (priorityEnum == null) { return null; }
        Priority priority = new Priority();
        priority.setName(priorityEnum.name());
        return priority;
    }
}
