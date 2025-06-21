//package com.pl.edu.wut.master.thesis.bug.service;
//
//import com.pl.edu.wut.master.thesis.bug.configuration.JiraClientConfiguration;
//import com.pl.edu.wut.master.thesis.bug.dto.request.ImportProjectRequest;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.*;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.nio.charset.StandardCharsets;
//import java.util.Base64;
//
//
//@Service
//@AllArgsConstructor
//public class JiraClientServiceNew {
//
//
//
//    private final RestTemplate restTemplate = new RestTemplate();
//    private final JiraClientService jiraClientService;
//    private final JiraClientConfiguration jiraClientConfiguration;
//
//    public String fetchProjectNew(ImportProjectRequest request) {
//        String baseUrl = request.getJiraBaseUrl();
//        if (!baseUrl.startsWith("http")) {
//            baseUrl = "https://" + baseUrl;
//        }
//
//        String jiraApiVersion = "3";
//        String url = baseUrl + "/rest/api/" + jiraApiVersion + "/project/" + request.getProjectKeyOrId();
//
//        // Use separate method to build headers
//        HttpHeaders headers = jiraClientConfiguration.buildJiraHeaders(request);
//
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
//
//        System.out.println(response.getBody());
//        return response.getBody();
//    }
//
//
//}