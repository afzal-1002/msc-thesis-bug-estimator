//package com.pl.edu.wut.master.thesis.bug.service.implementation;
//
//import com.pl.edu.wut.master.thesis.bug.configuration.JiraClientConfiguration;
//import com.pl.edu.wut.master.thesis.bug.model.comment.Comment;
//import com.pl.edu.wut.master.thesis.bug.model.comment.AddCommentRequest;
//import com.pl.edu.wut.master.thesis.bug.model.comment.CommentRequest;
//import com.pl.edu.wut.master.thesis.bug.model.comment.CommentResponse;
//import com.pl.edu.wut.master.thesis.bug.model.comment.CommentsList;
//import com.pl.edu.wut.master.thesis.bug.repository.CommentRepository;
//import com.pl.edu.wut.master.thesis.bug.service.CommentService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//@Log4j2
//public class CommentServiceImplementation implements CommentService {
//
//    private final CommentRepository       commentRepository;
//    private final JiraClientConfiguration jiraClientConfiguration;
//
//    @Override
//    public CommentResponse addComment(AddCommentRequest request) {
//        // 1) build & post to Jira
//        CommentRequest payload = CommentRequest.builder().body(request.getBody())
//                .visibility(request.getVisibility()).build();
//
//        log.info("Posting Jira comment to issue={} body={} visibility={}",
//                request.getIssueId(), request.getBody(), request.getVisibility());
//
//        CommentResponse jr = jiraClientConfiguration.post(
//                "/issue/" + request.getIssueId() + "/comment",
//                payload,
//                CommentResponse.class
//        );
//
//        // 2) map & save locally, reusing the same JIRA ID
//        Comment entity = Comment.builder()
//                .id(jr.getId())
//                .self(jr.getSelf())
//                .author(jr.getAuthor())
//                .updateAuthor(jr.getUpdateAuthor())
//                .created(jr.getCreated())
//                .updated(jr.getUpdated())
//                .visibility(jr.getVisibility())
//                .body( jr.getBody().toString() )
//                .build();
//
//        commentRepository.save(entity);
//        return jr;
//    }
//
//    @Override
//    public void syncComments(String issueKey) {
//        log.info("Synchronizing comments for Jira issue {}", issueKey);
//
//        // 1) fetch all comments from Jira
//        CommentsList page = jiraClientConfiguration.get("/issue/" + issueKey
//                + "/comment", CommentsList.class);
//
//        // 2) upsert each one by PK = JIRA comment ID
//        for (CommentResponse jr : page.getComments()) {
//            Comment entity = Comment.builder()
//                    .id(jr.getId())
//                    .self(jr.getSelf())
//                    .author(jr.getAuthor())
//                    .updateAuthor(jr.getUpdateAuthor())
//                    .created(jr.getCreated())
//                    .updated(jr.getUpdated())
//                    .visibility(jr.getVisibility())
//                    .body(jr.getBody().toString())
//                    .build();
//
//            commentRepository.save(entity);
//        }
//    }
//}
//
//
//
//
//
////
////    @Override
////    public CommentResponse getCommentById(Long id) {
////        Comment comment = commentRepository.findById(id)
////                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + id));
////        return convertToResponse(comment);
////    }
////
////    @Override
////    public CommentResponse getCommentByJiraId(String jiraCommentId) {
////        Comment comment = commentRepository.findByJiraCommentId(jiraCommentId)
////                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with Jira ID: " + jiraCommentId));
////        return convertToResponse(comment);
////    }
////
////    @Override
////    public List<CommentResponse> getCommentsByIssueKey(String issueKey) {
////        return commentRepository.findAllByIssueKey(issueKey).stream()
////                .map(this::convertToResponse)
////                .collect(Collectors.toList());
////    }
////
////    @Override
////    public List<CommentResponse> getCommentsByAuthor(Long authorId) {
////        return commentRepository.findAllByAuthor_Id(authorId).stream()
////                .map(this::convertToResponse)
////                .collect(Collectors.toList());
////    }
////
////    @Override
////    public CommentResponse updateComment(Long id, UpdateCommentRequest request) {
////        Comment comment = commentRepository.findById(id)
////                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + id));
////        Long userId = (Long) session.getAttribute("userId");
////
////        User currentUser = userService.findUserById(userId);
////
////        comment.setBody(request.getBody());
////        comment.setVisibility(request.getVisibility());
////        comment.setUpdateAuthor(currentUser);
////        comment.setUpdatedAt(LocalDateTime.now());
////        comment.setVersion(comment.getVersion() + 1);
////
////        Comment updatedComment = commentRepository.save(comment);
////        return convertToResponse(updatedComment);
////    }
////
////    @Override
////    public void deleteComment(Long id) {
////        Comment comment = commentRepository.findById(id)
////                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + id));
////        commentRepository.delete(comment);
////    }
////
////    @Override
////    public List<CommentResponse> getCommentsByJiraIds(List<String> jiraCommentIds) {
////        return commentRepository.findAllByJiraCommentIds(jiraCommentIds).stream()
////                .map(this::convertToResponse)
////                .collect(Collectors.toList());
////    }
////
////    private CommentResponse convertToResponse(Comment comment) {
////
////        User user = comment.getAuthor();
////        UserSummary author = userMapper.toUserSummary(user);
////
////        return CommentResponse.builder()
////                .id(comment.getId())
////                .jiraCommentId(comment.getJiraCommentId())
////                .body(comment.getBody())
////                .version(comment.getVersion())
////                .issueKey(comment.getIssueKey())
////                .visibility(comment.getVisibility())
////                .createdAt(comment.getCreatedAt())
////                .updatedAt(comment.getUpdatedAt())
////                .author(author)
////                .updateAuthor(author)
////                .self(comment.getSelf() != null ? comment.getSelf() : generateSelfUrl(comment.getIssueKey(), comment.getId()))
////                .build();
////    }
////
////    private String generateSelfUrl(String issueKey) {
////        return "/api/issues/" + issueKey + "/comments/";
////    }
////
////    private String generateSelfUrl(String issueKey, Long commentId) {
////        return generateSelfUrl(issueKey) + commentId;
////    }
