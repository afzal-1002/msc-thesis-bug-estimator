//package com.pl.edu.wut.master.thesis.bug.service.implementation;
//
//import com.pl.edu.wut.master.thesis.bug.dto.request.*;
//import com.pl.edu.wut.master.thesis.bug.dto.response.*;
//import com.pl.edu.wut.master.thesis.bug.service.JiraClientService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.*;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Collections;
//import java.util.List;
//
//
//@Service
//@RequiredArgsConstructor
//public class JiraClientServiceImpl implements JiraClientService {
//    private final RestTemplate restTemplate;
//
//    @Value("${jira.api.base-url}")
//    private String jiraBaseUrl;
//
//    private HttpHeaders createAuthHeaders(String username, String token) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBasicAuth(username, token);
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        return headers;
//    }
//
//    @Override
//    public ProjectResponse fetchProject(ProjectRequest request) {
//        String url = String.format("%s/rest/api/3/project/%s",
//                request.getBaseUrl(), request.getJiraId());
//
//        HttpHeaders headers = createAuthHeaders(
//                request.getJiraUsername(),
//                request.getUserToken()
//        );
//
//        ResponseEntity<ProjectResponse> response = restTemplate.exchange(
//                url,
//                HttpMethod.GET,
//                new HttpEntity<>(headers),
//                ProjectResponse.class
//        );
//
//        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
//            throw new RuntimeException("Failed to fetch project from Jira");
//        }
//        return response.getBody();
//    }
//
//    @Override
//    public List<String> fetchProjectStatuses(String projectKey, ProjectRequest credentials) {
//        String url = String.format("%s/rest/api/3/project/%s/statuses",
//                credentials.getBaseUrl(), projectKey);
//
//        return fetchJiraList(url, credentials);
//    }
//
//    @Override
//    public List<String> fetchProjectComponents(String projectKey, ProjectRequest credentials) {
//        String url = String.format("%s/rest/api/3/project/%s/components",
//                credentials.getBaseUrl(), projectKey);
//
//        return fetchJiraList(url, credentials);
//    }
//
//    @Override
//    public List<String> fetchProjectVersions(String projectKey, ProjectRequest credentials) {
//        String url = String.format("%s/rest/api/3/project/%s/versions",
//                credentials.getBaseUrl(), projectKey);
//
//        return fetchJiraList(url, credentials);
//    }
//
//    private List<String> fetchJiraList(String url, ProjectRequest credentials) {
//        HttpHeaders headers = createAuthHeaders(
//                credentials.getJiraUsername(),
//                credentials.getUserToken()
//        );
//
//        ResponseEntity<List<String>> response = restTemplate.exchange(
//                url,
//                HttpMethod.GET,
//                new HttpEntity<>(headers),
//                new ParameterizedTypeReference<>() {}
//        );
//
//        return response.getBody() != null ? response.getBody() : Collections.emptyList();
//    }
//
//    @Override
//    public void archiveProject(String projectKey, ProjectRequest credentials) {
//        String url = String.format("%s/rest/api/3/project/%s/archive",
//                credentials.getBaseUrl(), projectKey);
//
//        executeJiraAction(url, credentials, HttpMethod.POST);
//    }
//
//    @Override
//    public void restoreProject(String projectKey, ProjectRequest credentials) {
//        String url = String.format("%s/rest/api/3/project/%s/restore",
//                credentials.getBaseUrl(), projectKey);
//
//        executeJiraAction(url, credentials, HttpMethod.POST);
//    }
//
//    private void executeJiraAction(String url, ProjectRequest credentials, HttpMethod method) {
//        HttpHeaders headers = createAuthHeaders(
//                credentials.getJiraUsername(),
//                credentials.getUserToken()
//        );
//
//        restTemplate.exchange(
//                url,
//                method,
//                new HttpEntity<>(headers),
//                Void.class
//        );
//    }
//}