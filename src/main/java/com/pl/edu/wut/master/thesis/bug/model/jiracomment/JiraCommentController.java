package com.pl.edu.wut.master.thesis.bug.model.jiracomment;

import com.pl.edu.wut.master.thesis.bug.dto.comment.CommentResponseWithIssueSummary;
import com.pl.edu.wut.master.thesis.bug.model.comment.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jira/comments")
@RequiredArgsConstructor
public class JiraCommentController {

    private final JiraCommentService jiraCommentService;

    @PostMapping("/save/{issueKey}")
    public ResponseEntity<CommentResponseWithIssueSummary> saveCommentInJiraAndDb( @PathVariable String issueKey, @RequestBody AddCommentRequest request) {

        CommentResponseWithIssueSummary savedComment = jiraCommentService.saveCommentInJiraAndDb(issueKey, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
    }

    @PutMapping("/update/{issueKey}/{commentId}")
    public ResponseEntity<Comment> updateCommentInJiraAndDb( @PathVariable String issueKey,
                                                             @PathVariable String commentId,
                                                             @RequestBody AddCommentRequest request) {

        Comment updatedComment = jiraCommentService.updateCommentInJiraAndDb(issueKey, commentId, request);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/delete/{issueKey}/{commentId}")
    public ResponseEntity<Void> deleteCommentInJiraAndDb(
            @PathVariable String issueKey,
            @PathVariable String commentId) {

        jiraCommentService.deleteCommentInJiraAndDb(issueKey, commentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/issues/{issueKey}/comments")
    public ResponseEntity<IssueCommentsResponse> getCommentsByKeyFromJira(@PathVariable String issueKey) {
        IssueCommentsResponse response = jiraCommentService.getCommentsByKeyFromJira(issueKey);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/issues/{issueKey}/comments/{commentId}")
    public ResponseEntity<AddCommentResponse> getCommentByKeyAndCommentIdFromJira(@PathVariable String issueKey, @PathVariable String commentId) {
        AddCommentResponse response = jiraCommentService.getCommentByKeyAndCommentIdFromJira(issueKey, commentId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/issues/{issueKey}/comments")
    public ResponseEntity<AddCommentResponse> saveCommentToJira(@PathVariable String issueKey, @RequestBody AddCommentRequest request) {
        AddCommentResponse response = jiraCommentService.saveCommentToJira(issueKey, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/issues/{issueKey}/comments/{commentId}")
    public ResponseEntity<AddCommentResponse> updateCommentToJira(@PathVariable String issueKey, @PathVariable String commentId, @RequestBody AddCommentRequest request) {
        AddCommentResponse response = jiraCommentService.updateCommentToJira(issueKey, commentId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/issues/{issueKey}/comments/{commentId}")
    public ResponseEntity<Void> deleteCommentFromJira(@PathVariable String issueKey, @PathVariable String commentId) {
        jiraCommentService.deleteCommentFromJira(issueKey, commentId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/list")
    public ResponseEntity<CommentsByIdsResponse> getListOfCommentsFromJiraByCommentsIds(@RequestBody CommentListRequest request) {
        CommentsByIdsResponse response = jiraCommentService.getListOfCommentsFromJiraByCommentsIds(request.getIds());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/sync/local-to-jira/{issueKey}")
    public ResponseEntity<List<Comment>> synchronizeCommentsFromLocalToJira(@PathVariable String issueKey) {
        List<Comment> comments = jiraCommentService.synchronizeCommentsFromLocalToJira(issueKey);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/sync/local-to-jira")
    public ResponseEntity<Void> syncCommentsFromLocalToJira() {
        jiraCommentService.syncCommentsFromLocalToJira();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sync/jira-to-local/{issueKey}")
    public ResponseEntity<List<CommentResponseWithIssueSummary>> synchronizeCommentsFromJiraToLocal(@PathVariable String issueKey) {
        List<CommentResponseWithIssueSummary> commentResponseWithIssueSummaries = jiraCommentService.synchronizeCommentsFromJiraToLocal(issueKey);
        return ResponseEntity.ok(commentResponseWithIssueSummaries);
    }


}