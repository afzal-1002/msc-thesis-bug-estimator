package com.pl.edu.wut.master.thesis.bug.mapper;

import com.pl.edu.wut.master.thesis.bug.dto.request.CommentRequest;
import com.pl.edu.wut.master.thesis.bug.dto.response.CommentResponse;
import com.pl.edu.wut.master.thesis.bug.model.comment.Comment;
import com.pl.edu.wut.master.thesis.bug.model.issue.Issue;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CommentMapper {

    private final UserSummaryMapper userSummaryMapper;

    public Comment toEntity(CommentRequest dto) {
        if (dto == null) return null;

        Issue issue = dto.getIssueId() != null ? Issue.builder().id(dto.getIssueId()).build() : null;

        Comment.CommentBuilder builder = Comment.builder();
        builder.id(null);
        builder.issue(issue);
        builder.author(userSummaryMapper.toEntity(dto.getAuthor()));
        builder.body(dto.getBody());
        builder.createdDate(dto.getCreatedDate() != null ? dto.getCreatedDate() : new java.util.Date());
        builder.updatedDate(dto.getUpdatedDate());
        builder.aiGenerated(dto.getAiGenerated() != null && dto.getAiGenerated());
        builder.jiraCommentId(dto.getJiraCommentId());
        return builder.build();
    }


    public CommentResponse toResponse(Comment entity) {
        if (entity == null) return null;
        CommentResponse.CommentResponseBuilder builder = CommentResponse.builder();
        builder.issueId(entity.getId());
        builder.author(userSummaryMapper.toResponse(entity.getAuthor()));
        builder.body(entity.getBody());
        builder.createdDate(entity.getCreatedDate());
        builder.updatedDate(entity.getUpdatedDate());
        builder.aiGenerated(entity.isAiGenerated());
        builder.jiraCommentId(entity.getJiraCommentId());
        return builder.build();
    }

    public Comment toEntity(CommentResponse dto) {
        if (dto == null) return null;

        Comment.CommentBuilder builder = Comment.builder();
        builder.id(dto.getIssueId());
        builder.body(dto.getBody());
        builder.createdDate(dto.getCreatedDate());
        builder.updatedDate(dto.getUpdatedDate());
        builder.aiGenerated(dto.getAiGenerated() != null && dto.getAiGenerated());
        builder.jiraCommentId(dto.getJiraCommentId());
        Comment comment = builder.build();

        // Map embedded author
        comment.setAuthor(dto.getAuthor() != null ? userSummaryMapper.toEntity(dto.getAuthor()) : null);

        // If your CommentResponse carried an issueId, you could stub it here:
         if (dto.getIssueId() != null) {
             comment.setIssue(Issue.builder().id(dto.getIssueId()).build());
         }

        return comment;
    }

}
