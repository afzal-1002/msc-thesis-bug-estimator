
package com.pl.edu.wut.master.thesis.bug.service;

import com.pl.edu.wut.master.thesis.bug.dto.request.CommentRequest;
import com.pl.edu.wut.master.thesis.bug.dto.response.CommentResponse;

import java.util.List;

public interface CommentService {
    List<CommentResponse> getComments(String issueKey);
    CommentResponse addComment(String issueKey, CommentRequest request);
    CommentResponse updateComment(String issueKey, Long commentId, CommentRequest request);
    void deleteComment(String issueKey, Long commentId);
    List<Object> getCommentProperties(Long commentId);
    Object getCommentProperty(Long commentId, String propertyKey);
    void setCommentProperty(Long commentId, String propertyKey, Object value);
    void deleteCommentProperty(Long commentId, String propertyKey);
}
