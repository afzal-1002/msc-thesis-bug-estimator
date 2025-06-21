package com.pl.edu.wut.master.thesis.bug.service.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pl.edu.wut.master.thesis.bug.configuration.JiraClientConfiguration;
import com.pl.edu.wut.master.thesis.bug.dto.request.ImportProjectRequest;
import com.pl.edu.wut.master.thesis.bug.dto.response.ProjectResponse;
import com.pl.edu.wut.master.thesis.bug.model.jira.JiraCredentials;
import com.pl.edu.wut.master.thesis.bug.service.JiraClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
@Slf4j
public class JiraClientServiceImplementation implements JiraClientService {

    @Value("${jira.api-version}")
    private String jiraApiVersion;

    private final JiraClientConfiguration jiraClientConfiguration;
    private final RestTemplate restTemplate;

    @Override
    public ProjectResponse fetchProject(ImportProjectRequest request) {

        // Use separate method to build headers
        HttpHeaders headers = jiraClientConfiguration.buildJiraHeaders(request);

        String baseUrl = jiraClientConfiguration.getBaseUrl(request);
        String path = baseUrl + "/project/" + request.getProjectKeyOrId();


        JiraCredentials jiraCredentials = new JiraCredentials(
                request.getJiraBaseUrl(),
                request.getJiraUsername(),
                request.getJiraToken()
        );

        ProjectResponse projectResponse =  jiraClientConfiguration.get(jiraCredentials, path, ProjectResponse.class);
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = null;
        try {
            jsonResponse = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(projectResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Successfully fetched project Json: " + jsonResponse);


        System.out.println(projectResponse);
        return projectResponse;
    }

    @Override
    public ProjectResponse fetchProjectForUser(ImportProjectRequest request) {

        ProjectResponse projectResponse =  fetchProject(request);
        ObjectMapper mapper = new ObjectMapper();

        String jsonResponse = null;
        try {
            jsonResponse = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(projectResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Successfully fetched project Json: " + jsonResponse);

        return (projectResponse);
    }






}
