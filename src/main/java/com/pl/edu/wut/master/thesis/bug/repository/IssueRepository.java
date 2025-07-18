package com.pl.edu.wut.master.thesis.bug.repository;

import com.pl.edu.wut.master.thesis.bug.model.issue.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IssueRepository extends JpaRepository<Issue, Long> {
    boolean existsByKey(String key);
    List<Issue> findByProjectKey(String projectKey);
    List<Issue> findByAssigneeAccountId(String accountId);
    Optional<Issue> findByKey(String key);
    List<Issue> findByProjectId(Long projectId);
    List<Issue> findAllByProject_Key(String projectKey);
    List<Issue> findAllByAssignee_AccountId(String accountId);
    List<Issue> findAllByProject_Id(Long projectId);
}
