// com/pl/edu/wut/master/thesis/bug/service/JiraCommentService.java
package com.pl.edu.wut.master.thesis.bug.service;

import com.pl.edu.wut.master.thesis.bug.dto.comment.CommentResponse;
import com.pl.edu.wut.master.thesis.bug.model.comment.CommentRequest;
import com.pl.edu.wut.master.thesis.bug.model.comment.CreateCommentRequest;
import com.pl.edu.wut.master.thesis.bug.model.comment.JiraComment;

import java.util.List;

public interface JiraCommentService {
    List<CommentResponse> getComments(String issueKey);
    CommentResponse      getComment(String issueKey, String commentId);
    CommentResponse      addComment(String issueKey, CommentRequest request);
    CommentResponse      updateComment(String issueKey, String commentId, CreateCommentRequest request);
    JiraComment          postComment(String issueKey, String body);
    JiraComment          updateComment(String issueKey, String commentId, String body);
    void                 deleteComment(String issueKey, String commentId);
}
