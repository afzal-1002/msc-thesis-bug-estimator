// src/main/java/com/pl/edu/wut/master/thesis/bug/service/implementation/JiraUserServiceImplementation.java
package com.pl.edu.wut.master.thesis.bug.service.implementation;

import com.pl.edu.wut.master.thesis.bug.configuration.JiraClientConfiguration;
import com.pl.edu.wut.master.thesis.bug.model.user.JiraUserResponse;
import com.pl.edu.wut.master.thesis.bug.model.user.UserCredential;
import com.pl.edu.wut.master.thesis.bug.repository.UserCredentialRepository;
import com.pl.edu.wut.master.thesis.bug.service.JiraUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JiraUserServiceImplementation implements JiraUserService {

    private final JiraClientConfiguration jiraClientConfiguration;
    private final UserCredentialRepository credentialRepository;

    @Override
    public JiraUserResponse getUserDetailsFromJira() {
        return jiraClientConfiguration.get("/myself", JiraUserResponse.class);
    }

    @Override
    public UserCredential getUserByUsername(String jiraUsername) {
        return credentialRepository.findByUsername(jiraUsername)
                .orElse(null);
    }


}
