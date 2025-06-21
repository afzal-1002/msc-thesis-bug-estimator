package com.pl.edu.wut.master.thesis.bug.model.version;

import com.pl.edu.wut.master.thesis.bug.model.project.Project;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "versions")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Version {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jira_version_id", nullable = false, unique = true)
    private String jiraVersionId;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
}
