
package com.pl.edu.wut.master.thesis.bug.model.user;

import com.pl.edu.wut.master.thesis.bug.model.project.Project;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "account_id", unique = true)
    private String accountId;

    @Column(name = "user_name", nullable = false, unique = true)
    private String username;

    @Column(name = "user_password", nullable = false)
    private String password;

    @Column(name = "user_email", nullable = false)
    private String email;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable( name = "user_projects",  joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    @Builder.Default
    private Set<Project> projects = new HashSet<>();

    // Consider adding helper methods for synchronization
    public void addProject(Project project) {
        projects.add(project);
        project.getUsers().add(this);
    }

    public void removeProject(Project project) {
        projects.remove(project);
        project.getUsers().remove(this);
    }
}
