// src/main/java/com/pl/edu/wut/master/thesis/bug/repository/ProjectRepository.java
package com.pl.edu.wut.master.thesis.bug.repository;


import com.pl.edu.wut.master.thesis.bug.model.project.Project;
import com.pl.edu.wut.master.thesis.bug.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;


public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByJiraId(String jiraId);
    Optional<Project> findByKey(String key);  // New method
    List<Project> findByUsersContaining(User user);
}
