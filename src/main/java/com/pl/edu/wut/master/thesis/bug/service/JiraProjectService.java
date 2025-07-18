package com.pl.edu.wut.master.thesis.bug.service;

import com.pl.edu.wut.master.thesis.bug.dto.project.CreateProjectRequest;
import com.pl.edu.wut.master.thesis.bug.dto.project.CreateProjectResponse;
import com.pl.edu.wut.master.thesis.bug.dto.project.ProjectDetailsSummary;
import com.pl.edu.wut.master.thesis.bug.dto.project.ProjectSummary;
import com.pl.edu.wut.master.thesis.bug.model.user.User;

import java.util.List;
import java.util.Map;

public interface JiraProjectService {

    /** Fetch a project by key or numeric ID. */
    ProjectDetailsSummary getProjectDetailsByKeyOrId(String projectKeyOrId);

    /** List all project keys visible to the authenticated user. */
    List<String> listProjectKeys();

    /** List all projects (as DTOs). */
    List<ProjectSummary> listProjects();

    /** Check whether a given key is valid in Jira. */
    Map<String, Object> validateProjectKey(String key);

    /** Generate a Jira-compliant project key. */
    String generateProjectKey(String desiredKey);

    /** Generate a Jira-compliant project name. */
    String generateProjectName(String desiredName);

    /** Create a new project in Jira. */
    CreateProjectResponse createProject(CreateProjectRequest request, User currentUser);

    List<ProjectSummary>       getAllProjectsFromJira();
}
