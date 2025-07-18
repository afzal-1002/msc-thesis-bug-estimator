package com.pl.edu.wut.master.thesis.bug.model.issue;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pl.edu.wut.master.thesis.bug.model.common.Document;
import com.pl.edu.wut.master.thesis.bug.model.issuetype.IssueTypeReference;
import com.pl.edu.wut.master.thesis.bug.model.project.ProjectReference;
import com.pl.edu.wut.master.thesis.bug.model.user.UserReference;
import lombok.Data;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor @AllArgsConstructor @Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateIssueFields {

    private ProjectReference project;

    @JsonProperty("issuetype")
    private IssueTypeReference issuetype;

    private String              summary;
    private String              duedate;

    private UserReference assignee;
    private Document description;

    private List<String>        labels;







}
