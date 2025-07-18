package com.pl.edu.wut.master.thesis.bug.model.issue;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pl.edu.wut.master.thesis.bug.dto.project.ProjectSummary;
import com.pl.edu.wut.master.thesis.bug.model.common.*;
import com.pl.edu.wut.master.thesis.bug.model.component.ComponentSummary;
import com.pl.edu.wut.master.thesis.bug.model.common.Description;
import com.pl.edu.wut.master.thesis.bug.model.user.UserSummary;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IssueFieldsOld {

    // Standard fields
    private IssueType issuetype;
    private Integer timespent;
    private ProjectSummary project;
    private List<Version> fixVersions;
    private Integer aggregatetimespent;
    private StatusCategory statusCategory;
    private Resolution resolution;
    private OffsetDateTime resolutionDate;
    private Integer workratio;
    private Watches watches;
    private OffsetDateTime lastViewed;
    private OffsetDateTime created;
    private Priority priority;
    private List<String> labels;
    private Integer timeEstimate;
    private Integer aggregateTimeOriginalEstimate;
    private List<Version> versions;
    private List<IssueLink> issuelinks;

    private Integer aggregateTimeEstimate;
    private OffsetDateTime statusCategoryChangedDate;
    @JsonProperty("timeoriginalestimate")
    private Integer timeOriginalEstimate;

    private Parent parent;

    private OffsetDateTime updated;
    private StatusDetails status;
    private List<ComponentSummary> components;
    private Description description;
    private TimeTracking timetracking;
    private String summary;

    private List<SubTask> subtasks;

    private UserSummary assignee;
    private UserSummary creator;
    private UserSummary reporter;

    private AggregateProgress aggregateprogress;
    private String environment;
    private LocalDate duedate;
    private Progress progress;
    private Votes votes;
    private CommentWrapper comment;
    private WorklogWrapper worklog;

    @JsonProperty("attachment")
    private List<Attachment> attachments;


    // Custom fields
    @JsonProperty("customfield_10001") private Object customfield10001;
    @JsonProperty("customfield_10002") private Object customfield10002;
    @JsonProperty("customfield_10003") private Object customfield10003;
    @JsonProperty("customfield_10004") private Object customfield10004;
    @JsonProperty("customfield_10005") private Object customfield10005;
    @JsonProperty("customfield_10006") private Object customfield10006;
    @JsonProperty("customfield_10007") private Object customfield10007;
    @JsonProperty("customfield_10008") private Object customfield10008;
    @JsonProperty("customfield_10009") private Object customfield10009;
    @JsonProperty("customfield_10010") private Object customfield10010;
    @JsonProperty("customfield_10014") private Object customfield10014;
    @JsonProperty("customfield_10015") private Object customfield10015;
    @JsonProperty("customfield_10016") private Object customfield10016;
    @JsonProperty("customfield_10017") private Object customfield10017;
    @JsonProperty("customfield_10019") private Object customfield10019;
    @JsonProperty("customfield_10020") private Object customfield10020;
    @JsonProperty("customfield_10021") private Object customfield10021;
    @JsonProperty("customfield_10022") private Object customfield10022;
    @JsonProperty("customfield_10023") private Object customfield10023;
    @JsonProperty("customfield_10024") private Object customfield10024;
    @JsonProperty("customfield_10025") private Object customfield10025;
    @JsonProperty("customfield_10026") private Object customfield10026;
    @JsonProperty("customfield_10027") private Object customfield10027;
    @JsonProperty("customfield_10028") private Object customfield10028;
    @JsonProperty("customfield_10029") private Object customfield10029;
    @JsonProperty("customfield_10030") private Object customfield10030;
    @JsonProperty("customfield_10031") private Object customfield10031;
    @JsonProperty("customfield_10032") private Object customfield10032;
    @JsonProperty("customfield_10033") private Object customfield10033;
    @JsonProperty("customfield_10034") private Object customfield10034;

    // Catch-all for any other custom fields
    @JsonAnySetter
    private Map<String, Object> additionalCustomFields = new HashMap<>();
}
