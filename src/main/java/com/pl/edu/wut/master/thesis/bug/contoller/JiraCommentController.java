//package com.pl.edu.wut.master.thesis.bug.contoller;
//
//
//import com.pl.edu.wut.master.thesis.bug.configuration.JiraClientConfiguration;
//import com.pl.edu.wut.master.thesis.bug.dto.comment.*;
//import com.pl.edu.wut.master.thesis.bug.model.comment.CommentRequest;
//import com.pl.edu.wut.master.thesis.bug.model.common.Document;
//import com.pl.edu.wut.master.thesis.bug.model.common.Visibility;
//import com.pl.edu.wut.master.thesis.bug.service.JiraCommentService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.*;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.util.UriUtils;
//
//import java.nio.charset.StandardCharsets;
//import java.util.Base64;
//import java.util.Collections;
//import java.util.List;
//import java.util.Map;
//
//import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
//
//@RestController
//@RequestMapping("/api/jira/issues")
//@RequiredArgsConstructor
//@Slf4j
//public class JiraCommentController {
//
//    private final JiraCommentService jiraCommentService;
//    private final JiraClientConfiguration jiraClientConfiguration;
//
//    private String baseUrl = "https://bugresolution.atlassian.net";
//    private String username = "01108500@pw.edu.pl";
//    private String token= "ATATT3xFfGF0AbqX3XAKoCz-detHxlJs3RMe-zapZETh0rKfG2kJtj4dz2LB1GnA6d8OWX46ddnzYVluE-2zbq91p2B4ywePNNBjvIY5ARryu9Gny6Wy2qHpsnkCn9Ke2_yqQR2j1nd8VP2CXuv50NI7YbNI1QGHT_H4V4iJu8a25yq694xaP5o=3DF35505";
//
//
////    @PostMapping("/hardcoded")
////    public String addHardcodedComment(@RequestBody CommentRequest commentRequest) {
////        // 1) Hard‑coded issueKey and text
////        String issueKey = "BUG-5";
////        String commentText = "This is a hard‑coded test comment from Spring Boot!";
////
////        // 2) Build the ADF document
////        Document.Paragraph.ContentItem textItem = Document.Paragraph.ContentItem.builder()
////                .type("text")
////                .text(commentText)
////                .build();
////
////        Document.Paragraph paragraph = Document.Paragraph.builder()
////                .type("paragraph")
////                .content(List.of(textItem))
////                .build();
////
////        Document body = Document.builder()
////                .type("doc")
////                .version(1)
////                .content(List.of(paragraph))
////                .build();
////
////        // 3) Build visibility (classic Jira example)
////        Visibility visibility = new Visibility();
////        visibility.setType("role");
////        visibility.setValue("Administrators");
////        // visibility.setIdentifier("10002"); // uncomment if you prefer ID
////
////        // 4) Wrap into your CommentRequest
////        CommentRequest request = CommentRequest.builder()
////                .body(body)
////                .visibility(visibility)
////                .build();
////
////        // 5) Call the service
////        jiraCommentService.addComment(issueKey, request);
////
////        // 6) Log to console and return a simple message
////        System.out.println("▶️ Hard‑coded comment posted to " + issueKey);
////        return "Hard‑coded comment posted to " + issueKey;
////    }
//
//
//
//
//
//
//
//
//}