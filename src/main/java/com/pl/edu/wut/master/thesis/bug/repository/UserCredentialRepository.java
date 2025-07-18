package com.pl.edu.wut.master.thesis.bug.repository;

import com.pl.edu.wut.master.thesis.bug.model.user.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCredentialRepository extends JpaRepository<UserCredential, Long> {
    Optional<UserCredential> findByUserId(Long userId);
    Optional<UserCredential> findByUsername(String jiraUsername);
//    Optional<UserCredential> findByUsername(String username);
//    Optional<UserCredential> findByEmail(String email);
//    Optional<UserCredential> findByUsernameAndJiraUsername(String username, String jiraUsername);


    Optional<UserCredential> findByUser_AccountId(String accountId);
    Optional<UserCredential> findByUser_Id(Long userId);

    boolean existsByUsername(String jiraUsername);


}