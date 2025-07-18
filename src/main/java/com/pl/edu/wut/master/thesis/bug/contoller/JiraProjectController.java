package com.pl.edu.wut.master.thesis.bug.contoller;

import com.pl.edu.wut.master.thesis.bug.dto.project.CreateProjectRequest;
import com.pl.edu.wut.master.thesis.bug.dto.project.CreateProjectResponse;
import com.pl.edu.wut.master.thesis.bug.dto.project.ProjectDetailsSummary;
import com.pl.edu.wut.master.thesis.bug.dto.project.ProjectSummary;
import com.pl.edu.wut.master.thesis.bug.model.user.User;
import com.pl.edu.wut.master.thesis.bug.service.JiraProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/jira/projects")
@RequiredArgsConstructor
public class JiraProjectController {

    private final JiraProjectService jiraProjectService;

    /** Get full details by key or ID */
    @GetMapping("/{projectKeyOrId}")
    public ProjectDetailsSummary getProjectDetails(
            @PathVariable String projectKeyOrId
    ) {
        return jiraProjectService.getProjectDetailsByKeyOrId(projectKeyOrId);
    }

    /** List just the project keys */
    @GetMapping("/keys")
    public List<String> listProjectKeys() {
        return jiraProjectService.listProjectKeys();
    }

    /** List all projects */
    @GetMapping
    public List<ProjectSummary> listProjects() {
        return jiraProjectService.listProjects();
    }

    /** Validate a project key */
    @GetMapping("/validate/key")
    public Map<String,Object> validateProjectKey(@RequestParam String key) {
        return jiraProjectService.validateProjectKey(key);
    }

    /** Suggest a valid project key */
    @GetMapping("/validate/project-key")
    public String generateProjectKey(@RequestParam String desiredKey) {
        return jiraProjectService.generateProjectKey(desiredKey);
    }

    /** Suggest a valid project name */
    @GetMapping("/validate/project-name")
    public String generateProjectName(@RequestParam String desiredName) {
        return jiraProjectService.generateProjectName(desiredName);
    }

    /** Create a new project (currentUser from security) */
    @PostMapping
    public CreateProjectResponse createProject(
            @RequestBody CreateProjectRequest request,
            Principal principal
    ) {
        // map principal → your User model however you like
        User currentUser = null;
        return jiraProjectService.createProject(request, currentUser);
    }

    /** (Optional) Future: fetch all projects from Jira directly */
    @GetMapping("/all")
    public List<ProjectSummary> getAllProjectsFromJira() {
        return jiraProjectService.getAllProjectsFromJira();
    }
}