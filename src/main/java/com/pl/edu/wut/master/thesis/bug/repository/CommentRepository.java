package com.pl.edu.wut.master.thesis.bug.repository;

import com.pl.edu.wut.master.thesis.bug.enums.SynchronizationStatus;
import com.pl.edu.wut.master.thesis.bug.model.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {

    List<Comment> findByIssueKey(String issueKey);

    List<Comment> findBySyncStatus(SynchronizationStatus syncStatus);

    List<Comment> findByIssueKeyAndSyncStatus(String issueKey, SynchronizationStatus syncStatus);

    @Query("SELECT c FROM Comment c JOIN FETCH c.issue WHERE c.syncStatus IS NULL")
    List<Comment> findUnsynchronizedCommentsWithIssue();
}

//    List<Comment> findAllByIssueKey(String issueKey);
//    void deleteAllByIssueKey(String issueKey);

//    // Lookup a single comment by its Jira ID
//    Optional<Comment> findByJiraCommentId(String jiraCommentId);
//
//    // Check existence by Jira ID
//    boolean existsByJiraCommentId(String jiraCommentId);
//
//    // All comments by a given author (by their PK)
//    List<Comment> findAllByAuthor_Id(Long authorId);
//
//    // All comments whose jiraCommentId is in this list
//    List<Comment> findAllByJiraCommentIdIn(List<String> jiraCommentIds);
//
//    // All comments for a domain‐Issue, by its 'key' property
//    List<Comment> findAllByIssue_Key(String key);
