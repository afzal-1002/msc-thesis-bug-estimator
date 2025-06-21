package com.pl.edu.wut.master.thesis.bug.configuration;


import com.pl.edu.wut.master.thesis.bug.dto.request.ImportProjectRequest;
import com.pl.edu.wut.master.thesis.bug.model.jira.JiraCredentials;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class JiraClientConfiguration {

    @Value("${jira.api-version}")
    private String jiraApiVersion;

    private final RestTemplate restTemplate;

    public String getBaseUrl(ImportProjectRequest request) {
        String baseUrl = request.getJiraBaseUrl();
        if (!baseUrl.startsWith("http")) { baseUrl = "https://" + baseUrl; }

        return (baseUrl + "/rest/api/" + jiraApiVersion);
    }


    public HttpHeaders buildHeaders(JiraCredentials credentials) {
        return getHeaders(credentials.getJiraUsername(), credentials.getJiraToken());
    }

    public HttpHeaders buildJiraHeaders(ImportProjectRequest request) {
        return getHeaders(request.getJiraUsername(), request.getJiraToken());
    }

    public HttpHeaders getHeaders(String username, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String auth = username + ":" + token;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
        headers.set("Authorization", "Basic " + encodedAuth);

        return headers;
    }

    public <T> T get(JiraCredentials credentials, String path, Class<T> responseType) {
        HttpEntity<String> request = new HttpEntity<>(buildHeaders(credentials));
        ResponseEntity<T> response = restTemplate.exchange( path, HttpMethod.GET,  request, responseType);
        return response.getBody();
    }

    public <T, B> T post(JiraCredentials credentials, String path, B body, Class<T> responseType) {
        HttpEntity<B> request = new HttpEntity<>(body, buildHeaders(credentials));
        ResponseEntity<T> response = restTemplate.exchange( path, HttpMethod.POST, request, responseType );
        return response.getBody();
    }

    public <T, B> T put(JiraCredentials credentials, String path, B body, Class<T> responseType) {
        HttpEntity<B> request = new HttpEntity<>(body, buildHeaders(credentials));
        ResponseEntity<T> response = restTemplate.exchange( path, HttpMethod.PUT, request, responseType );
        return response.getBody();
    }

    public <T> T delete(JiraCredentials credentials, String path, Class<T> responseType) {
        HttpEntity<Void> request = new HttpEntity<>(buildHeaders(credentials));
        ResponseEntity<T> response = restTemplate.exchange( path, HttpMethod.DELETE, request, responseType );
        return response.getBody();
    }

    public <T, B> T patch(JiraCredentials credentials, String path, B body, Class<T> responseType) {
        HttpEntity<B> request = new HttpEntity<>(body, buildHeaders(credentials));
        ResponseEntity<T> response = restTemplate.exchange( path, HttpMethod.PATCH, request, responseType );
        return response.getBody();
    }
}
