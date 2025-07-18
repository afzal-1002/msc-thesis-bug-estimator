package com.pl.edu.wut.master.thesis.bug.model.issue;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pl.edu.wut.master.thesis.bug.enums.PriorityEnum;
import com.pl.edu.wut.master.thesis.bug.enums.Status;
import com.pl.edu.wut.master.thesis.bug.enums.SynchronizationStatus;
import com.pl.edu.wut.master.thesis.bug.model.ai.AIEstimation;
import com.pl.edu.wut.master.thesis.bug.model.comment.Comment;
import com.pl.edu.wut.master.thesis.bug.model.common.TimeTracking;
import com.pl.edu.wut.master.thesis.bug.model.issuetype.IssueType;
import com.pl.edu.wut.master.thesis.bug.model.project.Project;
import com.pl.edu.wut.master.thesis.bug.model.user.UserSummary;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
@Table(name = "issues")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Issue {

    @Id
    private Long id;

    @NaturalId
    @Column(name = "issue_key", unique = true)
    private String key;

    @Column(name = "self_url", columnDefinition = "TEXT")
    private String self;

    @Column(name = "summary", columnDefinition = "TEXT")
    private String summary;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_type_id", nullable = false)
    private IssueType issueType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PriorityEnum priority;

    @Column(columnDefinition = "TEXT")
    private String environment;

    @Column(name = "parent_key")
    private String parentKey;

    @ElementCollection
    @CollectionTable(name = "issue_labels", joinColumns = @JoinColumn(name = "issue_id"))
    @Column(name = "label")
    private List<String> labels = new ArrayList<>();

    @Embedded
    private TimeTracking timeTracking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    @JsonBackReference("project-issues")
    private Project project;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "self",         column = @Column(name = "assignee_self_url", columnDefinition = "TEXT")),
            @AttributeOverride(name = "accountId",    column = @Column(name = "assignee_account_id")),
            @AttributeOverride(name = "emailAddress", column = @Column(name = "assignee_email_address")),
            @AttributeOverride(name = "username",     column = @Column(name = "assignee_username")),
            @AttributeOverride(name = "displayName",  column = @Column(name = "assignee_display_name")),
            @AttributeOverride(name = "active",       column = @Column(name = "assignee_is_active")),
            @AttributeOverride(name = "accountType",  column = @Column(name = "assignee_account_type")),
            @AttributeOverride(name = "timeZone",     column = @Column(name = "assignee_time_zone")),
            @AttributeOverride(name = "avatarUrls.url48x48", column = @Column(name = "assignee_avatar_url_48x48", length = 500)),
            @AttributeOverride(name = "avatarUrls.url24x24", column = @Column(name = "assignee_avatar_url_24x24", length = 500)),
            @AttributeOverride(name = "avatarUrls.url16x16", column = @Column(name = "assignee_avatar_url_16x16", length = 500)),
            @AttributeOverride(name = "avatarUrls.url32x32", column = @Column(name = "assignee_avatar_url_32x32", length = 500))
    })
    private UserSummary assignee;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "self",         column = @Column(name = "reporter_self_url", columnDefinition = "TEXT")),
            @AttributeOverride(name = "accountId",    column = @Column(name = "reporter_account_id")),
            @AttributeOverride(name = "emailAddress", column = @Column(name = "reporter_email_address")),
            @AttributeOverride(name = "username",     column = @Column(name = "reporter_username")),
            @AttributeOverride(name = "displayName",  column = @Column(name = "reporter_display_name")),
            @AttributeOverride(name = "active",       column = @Column(name = "reporter_is_active")),
            @AttributeOverride(name = "accountType",  column = @Column(name = "reporter_account_type")),
            @AttributeOverride(name = "timeZone",     column = @Column(name = "reporter_time_zone")),
            @AttributeOverride(name = "avatarUrls.url48x48", column = @Column(name = "reporter_avatar_url_48x48", length = 500)),
            @AttributeOverride(name = "avatarUrls.url24x24", column = @Column(name = "reporter_avatar_url_24x24", length = 500)),
            @AttributeOverride(name = "avatarUrls.url16x16", column = @Column(name = "reporter_avatar_url_16x16", length = 500)),
            @AttributeOverride(name = "avatarUrls.url32x32", column = @Column(name = "reporter_avatar_url_32x32", length = 500))
    })
    private UserSummary reporter;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "resolved_at")
    private LocalDateTime resolvedAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "sync_status")
    private SynchronizationStatus syncStatus;

    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AIEstimation> aiEstimations = new HashSet<>();

    @Column(name = "due_date")
    private LocalDate dueDate;

}