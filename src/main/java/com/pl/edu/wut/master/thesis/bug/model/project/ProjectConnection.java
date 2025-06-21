package com.pl.edu.wut.master.thesis.bug.model.project;

import com.pl.edu.wut.master.thesis.bug.model.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "project_connections")
@Data
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ProjectConnection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    Long id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "user_id")
    private User owner;

    String baseUrl;
    String jiraUsername;
    String userToken;

    String projectKey;
    String projectName;

}