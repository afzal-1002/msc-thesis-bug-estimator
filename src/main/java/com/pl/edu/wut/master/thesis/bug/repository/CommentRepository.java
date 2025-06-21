package com.pl.edu.wut.master.thesis.bug.repository;

import com.pl.edu.wut.master.thesis.bug.model.comment.Comment;
import com.pl.edu.wut.master.thesis.bug.model.issue.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findByIdAndIssue(Long id, Issue issue);
    Collection<Comment> findByIssue(Issue issue);
}
