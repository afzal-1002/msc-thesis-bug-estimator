package com.pl.edu.wut.master.thesis.bug.service.implementation;

import com.pl.edu.wut.master.thesis.bug.configuration.JiraClientConfiguration;
import com.pl.edu.wut.master.thesis.bug.dto.project.CreateProjectRequest;
import com.pl.edu.wut.master.thesis.bug.dto.project.CreateProjectResponse;
import com.pl.edu.wut.master.thesis.bug.dto.project.ProjectDetailsSummary;
import com.pl.edu.wut.master.thesis.bug.dto.project.ProjectSummary;
import com.pl.edu.wut.master.thesis.bug.model.user.User;
import com.pl.edu.wut.master.thesis.bug.service.JiraProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JiraProjectServiceImplementation implements JiraProjectService {

    private final JiraClientConfiguration jiraClientConfiguration;

    @Override
    public ProjectDetailsSummary getProjectDetailsByKeyOrId(String projectKeyOrId) {
        return jiraClientConfiguration.get("/project/" + projectKeyOrId, ProjectDetailsSummary.class);
    }

    @Override
    public List<String> listProjectKeys() {
        ProjectSummary[] arr = jiraClientConfiguration.get("/project", ProjectSummary[].class);
        return arr == null ? List.of() : Arrays.stream(arr)
                .map(ProjectSummary::getKey)
                .toList();
    }

    @Override
    public List<ProjectSummary> listProjects() {
        ProjectSummary[] arr = jiraClientConfiguration.get("/project", ProjectSummary[].class);
        return arr == null ? List.of() : Arrays.asList(arr);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> validateProjectKey(String key) {
        String path = UriComponentsBuilder
                .fromPath("/projectvalidate/key")
                .queryParam("key", key)
                .toUriString();
        return jiraClientConfiguration.get(path, Map.class);
    }

    @Override
    public String generateProjectKey(String desiredKey) {
        String raw = jiraClientConfiguration.get(
                UriComponentsBuilder
                        .fromPath("/projectvalidate/validProjectKey")
                        .queryParam("key", desiredKey)
                        .toUriString(),
                String.class
        );
        return raw.replaceAll("^\"|\"$", "");
    }

    @Override
    public String generateProjectName(String desiredName) {
        String raw = jiraClientConfiguration.get(
                UriComponentsBuilder.fromPath("/projectvalidate/validProjectName")
                        .queryParam("name", desiredName)
                        .toUriString(),
                String.class
        );
        return raw.replaceAll("^\"|\"$", "");
    }

    @Override
    public CreateProjectResponse createProject(CreateProjectRequest request, User currentUser) {
        return jiraClientConfiguration.post("/project", request, CreateProjectResponse.class);
    }

    // This is the method that needed to be fixed.
    @Override
    public List<ProjectSummary> getAllProjectsFromJira() {
        ProjectSummary[] arr = jiraClientConfiguration.get("/project", ProjectSummary[].class);
        return arr == null ? List.of() : Arrays.asList(arr);
    }
}