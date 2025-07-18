package com.pl.edu.wut.master.thesis.bug.service;

import com.pl.edu.wut.master.thesis.bug.dto.user.UserCredentialResponse;
import com.pl.edu.wut.master.thesis.bug.model.user.UserCredential;
import org.springframework.stereotype.Service;

@Service
public interface UserCredentialService {
    UserCredentialResponse findByCredentialId(Long credentialId);
    UserCredentialResponse findByUserId(Long userId);
    boolean existsByJiraUsername(String jiraUsername);
    UserCredential findByJiraUsername(String jiraUsername);

}