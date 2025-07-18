package com.pl.edu.wut.master.thesis.bug.model.user;

import com.pl.edu.wut.master.thesis.bug.model.project.Project;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"projects", "jiraCredential"})
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    /** Natural key – stable and unique */
    @EqualsAndHashCode.Include
    @Column(name = "account_id", unique = true, nullable = false)
    private String accountId;

    @Column(name = "login_name", nullable = false, unique = true)
    private String logiName;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email_address", nullable = false, unique = true)
    private String emailAddress;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable( name = "project_user",
            joinColumns =  @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private Set<Project> projects = new HashSet<>();


    @OneToOne(mappedBy = "user",  fetch = FetchType.LAZY,  cascade = CascadeType.ALL, orphanRemoval = true)
    private UserCredential jiraCredential;

    public void addProject(Project project) {
        this.projects.add(project);
        project.getUsers().add(this);
    }

    public void removeProject(Project project) {
        this.projects.remove(project);
        project.getUsers().remove(this);
    }


}
