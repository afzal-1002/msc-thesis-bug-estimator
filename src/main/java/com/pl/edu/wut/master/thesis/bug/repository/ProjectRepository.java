package com.pl.edu.wut.master.thesis.bug.repository;

import com.pl.edu.wut.master.thesis.bug.model.project.Project;
import com.pl.edu.wut.master.thesis.bug.model.user.User;

import com.pl.edu.wut.master.thesis.bug.model.user.UserSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Optional<Project> findByKey(String key);
    Optional<Project> findByIdOrKey(Long id, String key);
    Optional<Project> findByKeyAndUsersContaining(String projectKey, User user);
    List<Project> findByLead(UserSummary lead);
    List<Project> findAllByUsers_AccountId(String accountId);
    List<Project> findAllByLead_AccountId(String accountId);

    @Query("SELECT DISTINCT p FROM Project p LEFT JOIN FETCH p.users")
    List<Project> findAllWithUsers();

    @Query("""
      SELECT DISTINCT p
        FROM Project p LEFT JOIN FETCH p.users LEFT JOIN FETCH p.issues LEFT JOIN FETCH p.lead
    """)
    List<Project> findAllWithUsersAndIssues();

    @Query("""
      SELECT p
        FROM Project p LEFT JOIN FETCH p.users LEFT JOIN FETCH p.issues WHERE p.id = :id
    """)
    Optional<Project> findByIdWithUsersAndIssues(@Param("id") Long id);

    @Query("""
      SELECT p FROM Project p LEFT JOIN FETCH p.users LEFT JOIN FETCH p.issues WHERE p.key = :key
    """)
    Optional<Project> findByKeyWithUsersAndIssues(@Param("key") String key);
}
