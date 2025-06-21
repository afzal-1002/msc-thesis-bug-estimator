package com.pl.edu.wut.master.thesis.bug.model.comment;

import com.pl.edu.wut.master.thesis.bug.model.issue.Issue;
import com.pl.edu.wut.master.thesis.bug.model.user.UserSummary;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "comments")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_id", nullable = false)
    private Issue issue;

    @Embedded
    private UserSummary author;

    @Column(length = 2000)
    private String body;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false, updatable = false)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "is_ai_generated")
    private boolean aiGenerated;

    @Column(name = "jira_comment_id", unique = true)
    private String jiraCommentId;
}
