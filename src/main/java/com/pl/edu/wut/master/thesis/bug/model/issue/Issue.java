package com.pl.edu.wut.master.thesis.bug.model.issue;

import com.pl.edu.wut.master.thesis.bug.enums.Priority;
import com.pl.edu.wut.master.thesis.bug.enums.Status;
import com.pl.edu.wut.master.thesis.bug.model.comment.Comment;
import com.pl.edu.wut.master.thesis.bug.model.component.ComponentInfo;
import com.pl.edu.wut.master.thesis.bug.model.project.Project;
import com.pl.edu.wut.master.thesis.bug.model.user.UserSummary;
import com.pl.edu.wut.master.thesis.bug.model.version.Version;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.TemporalType.TIMESTAMP;

@Entity
@Table(name = "issues")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class Issue {

    @Id @GeneratedValue
    private Long id;

    @Column(name="issue_key", unique=true, nullable=false)
    private String issueKey;

    private String summary;

    @Column(columnDefinition="TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="accountId", column=@Column(name="assignee_account_id")),
            @AttributeOverride(name="emailAddress", column=@Column(name="assignee_email_address")),
            @AttributeOverride(name="displayName", column=@Column(name="assignee_display_name"))
    })
    private UserSummary assignee;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="accountId", column=@Column(name="reporter_account_id")),
            @AttributeOverride(name="emailAddress", column=@Column(name="reporter_email_address")),
            @AttributeOverride(name="displayName", column=@Column(name="reporter_display_name"))
    })
    private UserSummary reporter;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToMany
    @JoinTable(
            name = "issue_components",
            joinColumns = @JoinColumn(name = "issue_id"),
            inverseJoinColumns = @JoinColumn(name = "component_id")
    )
    private Set<ComponentInfo> components = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "issue_versions",
            joinColumns = @JoinColumn(name = "issue_id"),
            inverseJoinColumns = @JoinColumn(name = "version_id")
    )
    private Set<Version> versions = new HashSet<>();

    @ElementCollection
    @CollectionTable(
            name = "issue_labels",
            joinColumns = @JoinColumn(name = "issue_id")
    )

    @Column(name = "label")
    private Set<String> labels = new HashSet<>();

    // — Time tracking from Jira —
    private Integer timeSpentSeconds;
    private Integer originalEstimateSeconds;
    private Integer remainingEstimateSeconds;
    private Integer aggTimeSpentSeconds;
    private Integer aggOriginalEstimateSeconds;
    private Integer aggRemainingEstimateSeconds;

    private Date dueDate;
    private Date jiraCreatedDate;
    private Date jiraUpdatedDate;

    // — AI estimates —
    private String aiEstimation;
    private Date aiEstimationDate;
    private Integer aiEstimationTotalTime;

    // — Comments (persisted) —
    @OneToMany(cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "issue_id", referencedColumnName = "id")
    private Set<Comment> comments = new HashSet<>();

    // — Audit —
    @Temporal(TIMESTAMP)
    private Date localCreated;

    @Temporal(TIMESTAMP)
    private Date localUpdated;

    @PrePersist void onCreate() {
        localCreated = localUpdated = new Date();
    }

    @PreUpdate  void onUpdate() {
        localUpdated = new Date();
    }
}
