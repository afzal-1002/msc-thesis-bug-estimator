//package com.pl.edu.wut.master.thesis.bug.service.implementation;
//
//import com.pl.edu.wut.master.thesis.bug.dto.request.CommentRequest;
//import com.pl.edu.wut.master.thesis.bug.dto.response.CommentResponse;
//import com.pl.edu.wut.master.thesis.bug.exception.ResourceNotFoundException;
//import com.pl.edu.wut.master.thesis.bug.mapper.CommentMapper;
//import com.pl.edu.wut.master.thesis.bug.model.comment.Comment;
//import com.pl.edu.wut.master.thesis.bug.model.issue.Issue;
//import com.pl.edu.wut.master.thesis.bug.repository.CommentRepository;
//import com.pl.edu.wut.master.thesis.bug.repository.IssueRepository;
//import com.pl.edu.wut.master.thesis.bug.service.CommentService;
//import com.pl.edu.wut.master.thesis.bug.service.JiraClientService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class CommentServiceImplementation implements CommentService {
//
//    private final CommentRepository commentRepository;
//    private final IssueRepository issueRepository;
//    private final CommentMapper commentMapper;
//    private final JiraClientService jiraClientService;
//
//    @Override
//    public List<CommentResponse> getComments(String issueKey) {
//        Optional<Issue> issue = issueRepository.findByIssueKey(issueKey);
//        if (issue.isEmpty()) {throw new ResourceNotFoundException("Issue not found: " + issueKey);}
//        return commentRepository.findByIssue(issue.orElse(null)).stream()
//                .map(commentMapper::toResponse)
//                .toList();
//    }
//
//    @Override
//    public CommentResponse addComment(String issueKey, CommentRequest request) {
//        Optional<Issue> issue = issueRepository.findByIssueKey(issueKey);
//        if (issue.isEmpty()) {throw new ResourceNotFoundException("Issue not found: " + issueKey);}
//        request.setIssueId(issue.get().getId());
//        Comment comment = commentMapper.toEntity(request);
//        commentRepository.save(comment);
//        CommentResponse jiraResp = jiraClientService.addComment(request);
//        comment.setJiraCommentId(jiraResp.getJiraCommentId());
//        commentRepository.save(comment);
//        return commentMapper.toResponse(comment);
//    }
//
//    @Override
//    public CommentResponse updateComment(String issueKey, Long commentId, CommentRequest request) {
//        Optional<Issue> issue = issueRepository.findByIssueKey(issueKey);
//        if (issue.isEmpty()) {throw new ResourceNotFoundException("Issue not found: " + issueKey);}
//        Comment existing = commentRepository.findByIdAndIssue(commentId, issue.orElse(null));
//        if (existing == null) {throw new ResourceNotFoundException("Comment not found: " + commentId);}
//        request.setIssueId(issue.get().getId());
//        request.setJiraCommentId(existing.getJiraCommentId());
//        CommentResponse jiraResp = jiraClientService.updateCommentInJira(request);
//        existing.setBody(request.getBody());
//        existing.setUpdatedDate(jiraResp.getUpdatedDate());
//        commentRepository.save(existing);
//        return commentMapper.toResponse(existing);
//    }
//
//    @Override
//    public void deleteComment(String issueKey, Long commentId) {
//        Optional<Issue> issue = issueRepository.findByIssueKey(issueKey);
//        if (issue.isEmpty()) { throw new ResourceNotFoundException("Issue not found: " + issueKey);}
//        Comment existing = commentRepository.findByIdAndIssue((commentId), issue.orElse(null));
//        if (existing == null) { throw new ResourceNotFoundException("Comment not found: " + commentId);}
//        CommentRequest req = CommentRequest.builder()
//                .issueId(issue.get().getId())
//                .jiraCommentId(existing.getJiraCommentId())
//                .build();
//        jiraClientService.deleteCommentInJira(req);
//        commentRepository.delete(existing);
//    }
//
//    @Override
//    public List<Object> getCommentProperties(Long commentId) {
//        return Collections.singletonList(jiraClientService.fetchCommentProperties(commentId));
//    }
//
//    @Override
//    public Object getCommentProperty(Long commentId, String propertyKey) {
//        return jiraClientService.fetchCommentProperty(commentId, propertyKey);
//    }
//
//    @Override
//    public void setCommentProperty(Long commentId, String propertyKey, Object value) {
//        jiraClientService.setCommentProperty(commentId, propertyKey, value);
//    }
//
//    @Override
//    public void deleteCommentProperty(Long commentId, String propertyKey) {
//        jiraClientService.deleteCommentProperty(commentId, propertyKey);
//    }
//}
