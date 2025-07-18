package com.pl.edu.wut.master.thesis.bug.service;

import com.pl.edu.wut.master.thesis.bug.model.comment.AddCommentRequest;
import com.pl.edu.wut.master.thesis.bug.model.comment.CommentResponse;
import com.pl.edu.wut.master.thesis.bug.model.comment.CreateCommentRequest;
import com.pl.edu.wut.master.thesis.bug.model.comment.UpdateCommentRequest;

import java.util.List;

public interface CommentService {
    CommentResponse addComment(AddCommentRequest request);
    void syncComments(String issueKey);

    CommentResponse createComment(String issueKey, CreateCommentRequest request);
    List<CommentResponse> listComments(String issueKey);
    CommentResponse getComment(String issueKey, String commentId);
    CommentResponse updateComment(String issueKey, String commentId, UpdateCommentRequest request);
    void deleteComment(String issueKey, String commentId);
    void synchronizeComments(String issueKey);


//    CommentResponse getCommentById(Long id);
//    CommentResponse getCommentByJiraId(String jiraCommentId);
//    List<CommentResponse> getCommentsByIssueKey(String issueKey);
//    List<CommentResponse> getCommentsByAuthor(Long authorId);
//    CommentResponse updateComment(Long id, UpdateCommentRequest request);
//    void deleteComment(Long id);
//    List<CommentResponse> getCommentsByJiraIds(List<String> jiraCommentIds);

}