package com.pl.edu.wut.master.thesis.bug.mapper;

import com.pl.edu.wut.master.thesis.bug.dto.issue.response.IssueResponse;
import com.pl.edu.wut.master.thesis.bug.dto.issue.response.IssueResponseSummary;
import com.pl.edu.wut.master.thesis.bug.dto.project.ProjectResponse;
import com.pl.edu.wut.master.thesis.bug.dto.project.ProjectSummary;
import com.pl.edu.wut.master.thesis.bug.model.common.Description;
import com.pl.edu.wut.master.thesis.bug.model.issue.IssueFields;
import com.pl.edu.wut.master.thesis.bug.model.user.UserSummary;
import com.pl.edu.wut.master.thesis.bug.service.IssueService;
import com.pl.edu.wut.master.thesis.bug.service.JiraIssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JiraIssueMapper {

    private final JiraIssueService jiraIssueService;
    private final IssueService issueService;

    /**
     * Maps a newly created Jira issue (from NewIssueResponse) into our IssueResponse,
     * fetching fresh fields from Jira via getIssueByKey.
     */
    public IssueResponse fromNewIssueToIssueResponse(IssueResponseSummary jira) {
        if (jira == null || jira.getKey() == null) {
            throw new IllegalArgumentException("Cannot map null IssueResponse");
        }

        IssueResponse fetchedFromJira = jiraIssueService.getIssueByKey(jira.getKey());

        return buildIssueResponse(jira.getId(), jira.getKey(), jira.getSelf(), fetchedFromJira.getFields());
    }

    /**
     * Maps a locally saved NewIssueResponse into our IssueResponse,
     * fetching fresh fields from our local DB via IssueService.
     */
    public IssueResponse toIssueResponse(IssueResponseSummary issueResponse) {
        if (issueResponse == null || issueResponse.getKey() == null) {
            throw new IllegalArgumentException("Cannot map null IssueResponse");
        }

        IssueResponse fetchedFromLocal = issueService.getIssueByKey(issueResponse.getKey());

        return buildIssueResponse( issueResponse.getId(), issueResponse.getKey(),
                issueResponse.getSelf(), fetchedFromLocal.getFields());
    }

    /**
     * Build an IssueResponse object manually, optionally transforming fields.
     */
    private IssueResponse buildIssueResponse( String id, String key, String self,
                                                    IssueFields fields) {

        if (fields == null) { fields = new IssueFields(); }

        // Here, you can transform or override any fields if needed
        IssueFields newFields = IssueFields.builder()
                .summary(fields.getSummary())
                .description(fields.getDescription())
                .status(fields.getStatus())
                .priority(fields.getPriority())
                .issuetype(fields.getIssuetype())
                .timespent(fields.getTimespent())
                .project(fields.getProject())
                .fixVersions(fields.getFixVersions())
                .versions(fields.getVersions())
                .aggregatetimespent(fields.getAggregatetimespent())
                .aggregateTimeOriginalEstimate(fields.getAggregateTimeOriginalEstimate())
                .timeOriginalEstimate(fields.getTimeOriginalEstimate())
                .timeEstimate(fields.getTimeEstimate())
                .statusCategory(fields.getStatusCategory())
                .resolution(fields.getResolution())
                .resolutionDate(fields.getResolutionDate())
                .workratio(fields.getWorkratio())
                .watches(fields.getWatches())
                .timetracking(fields.getTimetracking())
                .lastViewed(fields.getLastViewed())
                .labels(fields.getLabels())
                .issuelinks(fields.getIssuelinks())
                .subtasks(fields.getSubtasks())
                .assignee(fields.getAssignee())
                .reporter(fields.getReporter())
                .creator(fields.getCreator())
                .components(fields.getComponents())
                .environment(fields.getEnvironment())
                .created(fields.getCreated())
                .updated(fields.getUpdated())
                .duedate(fields.getDuedate())
                .aggregateprogress(fields.getAggregateprogress())
                .progress(fields.getProgress())
                .votes(fields.getVotes())
                .comment(fields.getComment())
                .worklog(fields.getWorklog())
                .customFields(fields.getCustomFields())
                .build();

        IssueResponse.IssueResponseBuilder builder = IssueResponse.builder();
        builder.id(id);
        builder.key(key);
        builder.self(self);
        builder.fields(newFields);
        return builder.build();
    }

    /**
     * Converts rich Jira description into plain text, if needed.
     */
    private String flattenDescription(Description description) {
        if (description == null || description.getContent() == null) { return null; }

        return description.getContent().stream()
                .flatMap(paragraph -> paragraph.getContent().stream())
                .map(Description.Content.ContentItem::getText)
                .filter(text -> text != null && !text.isEmpty())
                .collect(Collectors.joining("\n"));
    }

    /**
     * Maps ProjectSummary to ProjectResponse DTO.
     */
    private ProjectResponse mapProject(ProjectSummary project) {
        if (project == null) { return null; }
        ProjectResponse.ProjectResponseBuilder builder = ProjectResponse.builder();
        builder.id(project.getId() != null ? Long.valueOf(project.getId()) : null);
        builder.key(project.getKey());
        builder.name(project.getName());
        return builder.build();
    }

    private String getAccountId(UserSummary user) {
        return user != null ? user.getAccountId() : null;
    }
}
