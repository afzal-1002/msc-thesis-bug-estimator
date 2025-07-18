package com.pl.edu.wut.master.thesis.bug.contoller;

import com.pl.edu.wut.master.thesis.bug.model.comment.CommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/jira")
@RequiredArgsConstructor
public class CommentControllerTest {

    private final RestTemplate restTemplate;

//    @Value("${jira.base-url}")
//    private String baseUrl;
//
//    @Value("${jira.username}")
//    private String username;
//
//    @Value("${jira.api-token}")
//    private String apiToken;

    @PostMapping("/{issueKey}/comment")
    public ResponseEntity<String> addComment(
            @PathVariable String issueKey,
            @RequestBody CommentRequest commentRequest) {

        String url = baseUrl + "/rest/api/3/issue/" + issueKey + "/comment";

        // Create headers with basic auth
        String auth = username + ":" + apiToken;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Basic " + encodedAuth);

        // Ensure the request body matches exactly what JIRA expects
        Map<String, Object> requestBody = new HashMap<>();
        Map<String, Object> body = new HashMap<>();
        body.put("type", "doc");
        body.put("version", 1);

        List<Map<String, Object>> content = new ArrayList<>();
        Map<String, Object> paragraph = new HashMap<>();
        paragraph.put("type", "paragraph");

        List<Map<String, Object>> paragraphContent = new ArrayList<>();
        Map<String, Object> text = new HashMap<>();
        text.put("type", "text");
        text.put("text", commentRequest.getBody().getContent().get(0).getContent().get(0).getText());
        paragraphContent.add(text);

        paragraph.put("content", paragraphContent);
        content.add(paragraph);
        body.put("content", content);

        requestBody.put("body", body);

        Map<String, Object> visibility = new HashMap<>();
        visibility.put("type", "role");
        visibility.put("value", "Administrators");
        visibility.put("identifier", "Administrators"); // Added identifier
        requestBody.put("visibility", visibility);

        requestBody.put("jsdPublic", false); // Changed from public to jsdPublic

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    String.class);

            return ResponseEntity.ok(response.getBody());
        } catch (HttpStatusCodeException ex) {
            return ResponseEntity.status(ex.getStatusCode())
                    .body("JIRA API Error: " + ex.getResponseBodyAsString());
        }
    }
}