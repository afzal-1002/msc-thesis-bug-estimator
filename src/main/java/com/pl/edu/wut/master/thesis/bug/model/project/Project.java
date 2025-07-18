package com.pl.edu.wut.master.thesis.bug.model.project;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pl.edu.wut.master.thesis.bug.dto.project.ProjectSummary;
import com.pl.edu.wut.master.thesis.bug.model.common.AvatarUrls;
import com.pl.edu.wut.master.thesis.bug.model.issue.Issue;
import com.pl.edu.wut.master.thesis.bug.model.user.User;
import com.pl.edu.wut.master.thesis.bug.model.user.UserSummary;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    @Id
    @Column(name = "project_id")
    private Long id;

    @Column(name = "jira_id")
    private String jiraId;

    @Column(name = "project_key", unique = true, nullable = false)
    private String key;

    @Column(name = "project_name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "avatar_url")
    private AvatarUrls avatarUrl;

    @Column(name = "project_category")
    private String projectCategory;

    @Column(name = "is_simplified", nullable = false)
    private boolean simplified = false;

    // in Project
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="self",         column=@Column(insertable=false, updatable=false)),
            @AttributeOverride(name="accountId",    column=@Column(name="lead_account_id")),
            @AttributeOverride(name="emailAddress", column=@Column(name="lead_user_email")),
            @AttributeOverride(name="username",     column=@Column(name="lead_username")),
            @AttributeOverride(name="displayName",  column=@Column(name="lead_display_name")),
            @AttributeOverride(name="active",       column=@Column(name="lead_active")),
            @AttributeOverride(name="accountType",  column=@Column(name="lead_account_type")),
            @AttributeOverride(name="timeZone",     column=@Column(name="lead_time_zone"))
    })
    private UserSummary lead;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy="projects")
    @JsonIgnore
    private Set<User> users = new HashSet<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy="project", cascade=  CascadeType.ALL)
    @JsonManagedReference("project-issues")
    private Set<Issue> issues;


//    Extra
    @Column(name = "project_type_key")
    private String projectTypeKey;

    @Column(name = "base_url")
    private String baseUrl;

    public ProjectReference toReference() {
        return ProjectReference.builder()
                .id(this.id)
                .key(this.key)
                .build();
    }



}



//Name*
//Key*
//
//Add project details
//Required fields are marked with an asterisk *
//Name*
//WUT-THESIS
//Key*
//WUT
//
//Share settings with an existing project
//

//          "issueTypes": [
//                  { "id": "10001", "name": "Story", "subtask": false, … },
//                  { "id": "10002", "name": "Bug",   "subtask": false, … },
//                  { "id": "10003", "name": "Epic",  "subtask": false, … },
//                  { "id": "10004", "name": "Task",  "subtask": false, … },
//                  { "id": "10005", "name": "Sub-task","subtask": true, … }