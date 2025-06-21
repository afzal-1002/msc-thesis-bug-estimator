package com.pl.edu.wut.master.thesis.bug.model.component;

import com.pl.edu.wut.master.thesis.bug.model.user.UserSummary;
import com.pl.edu.wut.master.thesis.bug.model.project.Project;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bug_components")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ComponentInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jira_component_id", nullable = false)
    private String jiraComponentId;

    private String name;

    private String description;

    @Embedded
    private UserSummary lead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
}
