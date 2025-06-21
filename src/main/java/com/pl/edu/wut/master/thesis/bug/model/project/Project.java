// src/main/java/com/pl/edu/wut/master/thesis/bug/model/project/Project.java
package com.pl.edu.wut.master.thesis.bug.model.project;

import com.pl.edu.wut.master.thesis.bug.model.user.User;
import com.pl.edu.wut.master.thesis.bug.model.user.UserSummary;
import com.pl.edu.wut.master.thesis.bug.model.component.ComponentInfo;
import com.pl.edu.wut.master.thesis.bug.model.version.Version;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {
    @Id
    @Column(name="project_id")
    private Long id;

    @Column(name="jira_id", unique=true, nullable=false)
    private String jiraId;

    @Column(name = "project_key", nullable = false, unique = true)
    private String key;

    @Column(name = "project_name", nullable = false)
    private String name;

    @Column(name = "project_description", columnDefinition = "TEXT")
    private String description;

    @ManyToMany(mappedBy = "projects")
    @Builder.Default
    private Set<User> users = new HashSet<>();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "accountId", column = @Column(name = "lead_account_id")),
            @AttributeOverride(name = "emailAddress", column = @Column(name = "lead_email_address")),
            @AttributeOverride(name = "displayName", column = @Column(name = "lead_display_name"))
    })
    private UserSummary lead;


    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "category_id")),
            @AttributeOverride(name = "name", column = @Column(name = "category_name")), // Fixed name conflict
            @AttributeOverride(name = "description", column = @Column(name = "category_description"))
    })
    private ProjectCategory category;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ComponentInfo> components = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Version> versions = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "project_roles", joinColumns = @JoinColumn(name = "project_id"))
    @MapKeyColumn(name = "role_name")
    @Column(name = "role_url")
    @Builder.Default
    private Map<String, String> roles = new HashMap<>();

    @Column(name = "project_type_key")
    private String projectTypeKey;

    @Column(name = "base_url")
    private String baseUrl;

    @Column(name = "jira_username")
    private String jiraUsername;

    @Column(name = "user_token")
    private String userToken;
}