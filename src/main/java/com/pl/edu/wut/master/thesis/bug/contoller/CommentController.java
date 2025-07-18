//package com.pl.edu.wut.master.thesis.bug.contoller;
//
//import com.pl.edu.wut.master.thesis.bug.dto.comment.CommentResponse;
//import com.pl.edu.wut.master.thesis.bug.dto.comment.CreateCommentRequest;
//import com.pl.edu.wut.master.thesis.bug.dto.comment.UpdateCommentRequest;
//import com.pl.edu.wut.master.thesis.bug.model.comment.create.AddCommentRequest;
//import com.pl.edu.wut.master.thesis.bug.service.CommentService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.net.URI;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/issues/{issueKey}/comments")
//@RequiredArgsConstructor
//public class CommentController {
//
//    private final CommentService commentService;
//
//
//    @PostMapping("/add")
//    public ResponseEntity<com.pl.edu.wut.master.thesis.bug.model.comment.create.CommentResponse> addComment(
//            @RequestBody AddCommentRequest request
//    ) {
//        com.pl.edu.wut.master.thesis.bug.model.comment.create.CommentResponse created = commentService.addComment(request);
//        return ResponseEntity.status(HttpStatus.CREATED).body(created);
//    }
//
//    @PostMapping
//    public ResponseEntity<CommentResponse> createComment(
//            @PathVariable String issueKey,
//            @Valid @RequestBody CreateCommentRequest request) {
//        CommentResponse response = commentService.createComment(issueKey, request);
//        return ResponseEntity
//                .created(URI.create(response.getSelf()))
//                .body(response);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<CommentResponse>> getCommentsByIssue(
//            @PathVariable String issueKey) {
//        List<CommentResponse> responses = commentService.getCommentsByIssueKey(issueKey);
//        return ResponseEntity.ok(responses);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<CommentResponse> getCommentById(
//            @PathVariable String issueKey,
//            @PathVariable Long id) {
//        CommentResponse response = commentService.getCommentById(id);
//        return ResponseEntity.ok(response);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<CommentResponse> updateComment(
//            @PathVariable String issueKey,
//            @PathVariable Long id,
//            @Valid @RequestBody UpdateCommentRequest request) {
//        CommentResponse response = commentService.updateComment(id, request);
//        return ResponseEntity.ok(response);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteComment(@PathVariable String issueKey, @PathVariable Long id) {
//        commentService.deleteComment(id);
//        return ResponseEntity.noContent().build();
//    }
//}
//
