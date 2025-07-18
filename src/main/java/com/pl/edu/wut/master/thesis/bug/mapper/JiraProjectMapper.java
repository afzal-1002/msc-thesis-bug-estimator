package com.pl.edu.wut.master.thesis.bug.mapper;

import com.pl.edu.wut.master.thesis.bug.dto.project.JiraProjectResponse;
import com.pl.edu.wut.master.thesis.bug.dto.project.ProjectDetailsSummary;
import com.pl.edu.wut.master.thesis.bug.model.project.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JiraProjectMapper {

    public JiraProjectResponse combineProjectDetails(Project project, ProjectDetailsSummary jiraDetails) {
        JiraProjectResponse response = new JiraProjectResponse();

        // Set fields from jiraDetails
        response.setExpand(jiraDetails.getExpand());
        response.setSelf(jiraDetails.getSelf());
        response.setId(jiraDetails.getId());
        // Don't set key from jiraDetails - use project key instead
        response.setDescription(jiraDetails.getDescription());
        response.setLead(jiraDetails.getLead());
        response.setComponents(jiraDetails.getComponents());
        response.setIssueTypes(jiraDetails.getIssueTypes());
        response.setAssigneeType(jiraDetails.getAssigneeType());
        response.setVersions(jiraDetails.getVersions());
        // Don't set name from jiraDetails - use project name instead
        response.setRoles(jiraDetails.getRoles());
        response.setAvatarUrls(jiraDetails.getAvatarUrls());
        response.setProjectTypeKey(jiraDetails.getProjectTypeKey());
        response.setSimplified(jiraDetails.isSimplified());
        response.setStyle(jiraDetails.getStyle());
        response.setPrivate(jiraDetails.isPrivate());
        response.setProperties(jiraDetails.getProperties());

        // Set fields from project (these should override any jiraDetails values)
        response.setKey(project.getKey());
        response.setName(project.getName());

        return response;
    }
}