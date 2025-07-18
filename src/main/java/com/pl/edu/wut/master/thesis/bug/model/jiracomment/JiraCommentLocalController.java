package com.pl.edu.wut.master.thesis.bug.model.jiracomment;

import com.pl.edu.wut.master.thesis.bug.dto.comment.CommentResponseWithIssueSummary;
import com.pl.edu.wut.master.thesis.bug.model.comment.Comment;
import com.pl.edu.wut.master.thesis.bug.model.jiracomment.JiraCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/wut/comments")
@RequiredArgsConstructor
public class JiraCommentLocalController {

    private final JiraCommentService jiraCommentService;

    @PostMapping("/local")
    public ResponseEntity<Comment> createCommentInDb(@RequestBody Comment comment) {
        Comment savedComment = jiraCommentService.createCommentInDb(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
    }

    @GetMapping("/local/{commentId}")
    public ResponseEntity<Comment> getCommentByIdFromDb(@PathVariable String commentId) {
        Optional<Comment> comment = jiraCommentService.getCommentByIdFromDb(commentId);
        return comment.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/local")
    public ResponseEntity<Comment> updateCommentInDb(@RequestBody Comment comment) {
        Comment updatedComment = jiraCommentService.updateCommentInDb(comment);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/local/{commentId}")
    public ResponseEntity<Void> deleteCommentInDb(@PathVariable String commentId) {
        jiraCommentService.deleteCommentInDb(commentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/local/all")
    public ResponseEntity<List<Comment>> getAllCommentsFromDb() {
        List<Comment> comments = jiraCommentService.getAllCommentsFromDb();
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/local/issue/{issueKey}/summary")
    public ResponseEntity<List<CommentResponseWithIssueSummary>> getCommentsWithSummarizedIssue(@PathVariable String issueKey) {
        List<CommentResponseWithIssueSummary> comments = jiraCommentService.getCommentsWithSummarizedIssue(issueKey);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/local/mismatched/{issueKey}")
    public ResponseEntity<List<Comment>> getMismatchedCommentsBetweenJiraAndLocalDb(@PathVariable String issueKey) {
        List<Comment> comments = jiraCommentService.getMismatchedCommentsBetweenJiraAndLocalDb(issueKey);
        return ResponseEntity.ok(comments);
    }
}