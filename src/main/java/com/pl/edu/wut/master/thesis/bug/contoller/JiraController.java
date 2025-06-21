package com.pl.edu.wut.master.thesis.bug.contoller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pl.edu.wut.master.thesis.bug.dto.request.ImportProjectRequest;
import com.pl.edu.wut.master.thesis.bug.dto.response.ProjectResponse;
import com.pl.edu.wut.master.thesis.bug.service.JiraClientService;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/jira")
public class JiraController {

    private final JiraClientService jiraClientService;

    @PostMapping("/project")
    public ResponseEntity<ProjectResponse> fetchProject(@RequestBody ImportProjectRequest request) {
        ProjectResponse result = jiraClientService.fetchProject(request);
        return ResponseEntity.ok(result);
    }


    @PostMapping("/project-api")
    public ResponseEntity<ProjectResponse> getProjectForUser(@RequestBody ImportProjectRequest request) {
        ProjectResponse responseBody = jiraClientService.fetchProjectForUser(request);
//        ProjectResponse projectResponse = parseProjectResponse(responseBody);
        return ResponseEntity.ok(responseBody);
    }

    public ProjectResponse parseProjectResponse(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(responseBody, ProjectResponse.class);
        } catch (JsonProcessingException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
            return null;
        }
    }

}
