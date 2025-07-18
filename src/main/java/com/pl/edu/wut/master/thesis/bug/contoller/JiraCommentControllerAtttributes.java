//package com.pl.edu.wut.master.thesis.bug.controller;
//
//import com.pl.edu.wut.master.thesis.bug.configuration.JiraClientConfiguration;
//import com.pl.edu.wut.master.thesis.bug.dto.comment.CommentBody;
//import com.pl.edu.wut.master.thesis.bug.dto.comment.CommentResponse;
//import com.pl.edu.wut.master.thesis.bug.model.common.Visibility;
//import com.pl.edu.wut.master.thesis.bug.model.issuetype.CreateIssueTypeRequest;
//import com.pl.edu.wut.master.thesis.bug.model.issuetype.CreateIssueTypeResponse;
//import com.pl.edu.wut.master.thesis.bug.service.JiraCommentService;
//import com.fasterxml.jackson.annotation.*;
//import lombok.*;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.util.UriUtils;
//
//import java.nio.charset.StandardCharsets;
//
//@RestController
//@RequestMapping("/api/jira/comment")
//@RequiredArgsConstructor
//public class JiraCommentControllerAtttributes {
//
//    private final JiraClientConfiguration jiraClientConfiguration;
//    private final JiraCommentService jiraCommentService;
//
//    public CommentResponse addComment(String issueKey, CreateCommentRequest request) {
//        // Ensure visibility has all required fields
//        if (request.getVisibility() != null && request.getVisibility().getValue() == null) {
//            request.getVisibility().setValue(request.getVisibility().getIdentifier());
//        }
//
//        return jiraClientConfiguration.post(basePath(issueKey), request, CommentResponse.class);
//    }
//
//    private String basePath(String issueKey) {
//        String safeKey = UriUtils.encodePath(issueKey, StandardCharsets.UTF_8);
//        return "/issue/" + safeKey + "/comment";
//    }
//
//
////    @PostMapping("/{issueKey}")
////    public String createComment(@RequestBody CreateCommentRequest request, @PathVariable String issueKey) {
////         jiraClientConfiguration.post(basePath(issueKey), request, CommentResponse.class);
////         return issueKey;
////    }
//
//
//
//
//    // ─── Request DTO ────────────────────────────────────────────────────────────
//
//    @Data
//    @NoArgsConstructor
//    @AllArgsConstructor
//    @Builder
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    public static class CreateCommentRequest {
//        private CommentBody body;
//        private Visibility visibility;
//        @JsonProperty("public")
//        private Boolean    jsdPublic;
//    }
//
//    // ─── ADF Document Model ────────────────────────────────────────────────────
//
//}
