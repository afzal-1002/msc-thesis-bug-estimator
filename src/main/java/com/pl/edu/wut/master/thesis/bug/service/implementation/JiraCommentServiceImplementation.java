// com/pl/edu/wut/master/thesis/bug/service/implementation/JiraCommentServiceImplementation.java
package com.pl.edu.wut.master.thesis.bug.service.implementation;

import com.pl.edu.wut.master.thesis.bug.configuration.JiraClientConfiguration;
import com.pl.edu.wut.master.thesis.bug.dto.comment.CommentResponse;
import com.pl.edu.wut.master.thesis.bug.model.comment.CommentRequest;
import com.pl.edu.wut.master.thesis.bug.model.comment.CreateCommentRequest;
import com.pl.edu.wut.master.thesis.bug.model.comment.JiraComment;
import com.pl.edu.wut.master.thesis.bug.service.JiraCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@Service("jiraCommentServiceImplementation")
@RequiredArgsConstructor
public class JiraCommentServiceImplementation implements JiraCommentService {

    private final JiraClientConfiguration jiraClientConfiguration;

    private static class CommentsWrapper {
        public List<CommentResponse> comments;
    }

    private String basePath(String issueKey) {
        String safeKey = UriUtils.encodePath(issueKey, StandardCharsets.UTF_8);
        return "/issue/" + safeKey + "/comment";
    }

    @Override
    public CommentResponse addComment(String issueKey, CommentRequest request) {
        return jiraClientConfiguration.post(
                basePath(issueKey),
                request,
                CommentResponse.class
        );
    }


    @Override
    public List<CommentResponse> getComments(String issueKey) {
        CommentsWrapper wrapper =
                jiraClientConfiguration.get(basePath(issueKey), CommentsWrapper.class);
        return wrapper.comments;
    }

    @Override
    public CommentResponse getComment(String issueKey, String commentId) {
        String path = basePath(issueKey) +
                "/" + UriUtils.encodePath(commentId, StandardCharsets.UTF_8);
        return jiraClientConfiguration.get(path, CommentResponse.class);
    }


    @Override
    public CommentResponse updateComment(
            String issueKey,
            String commentId,
            CreateCommentRequest request
    ) {
        String path = basePath(issueKey) +
                "/" + UriUtils.encodePath(commentId, StandardCharsets.UTF_8);
        return jiraClientConfiguration.put(path, request, CommentResponse.class);
    }

    @Override
    public JiraComment postComment(String issueKey, String body) {
        var payload = Collections.singletonMap("body", body);
        return jiraClientConfiguration.post(
                basePath(issueKey),
                payload,
                JiraComment.class
        );
    }

    @Override
    public JiraComment updateComment(String issueKey, String commentId, String body) {
        var payload = Collections.singletonMap("body", body);
        String path = basePath(issueKey) +
                "/" + UriUtils.encodePath(commentId, StandardCharsets.UTF_8);
        return jiraClientConfiguration.put(path, payload, JiraComment.class);
    }

    @Override
    public void deleteComment(String issueKey, String commentId) {
        String path = basePath(issueKey) +
                "/" + UriUtils.encodePath(commentId, StandardCharsets.UTF_8);
        jiraClientConfiguration.delete(path, Void.class);
    }
}
