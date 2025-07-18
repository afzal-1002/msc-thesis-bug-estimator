package com.pl.edu.wut.master.thesis.bug.service.implementation;


import com.pl.edu.wut.master.thesis.bug.exception.ResourceNotFoundException;
import com.pl.edu.wut.master.thesis.bug.dto.user.UserCredentialResponse;
import com.pl.edu.wut.master.thesis.bug.model.user.UserCredential;
import com.pl.edu.wut.master.thesis.bug.repository.UserCredentialRepository;
import com.pl.edu.wut.master.thesis.bug.service.UserCredentialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserCredentialServiceImplementation implements UserCredentialService {

    private final UserCredentialRepository credentialRepository;

    @Override
    public UserCredentialResponse findByCredentialId(Long credentialId) {
        UserCredential credential = credentialRepository.findById(credentialId).orElseThrow(() ->
                        new ResourceNotFoundException("Credential not found with id: " + credentialId));
        return toCredentialsResponse(credential);
    }

    @Override
    public UserCredentialResponse findByUserId(Long userId) {
        UserCredential credential = credentialRepository.findByUserId(userId).orElseThrow(() ->
                        new ResourceNotFoundException("Credential not found for user id: " + userId));
        return toCredentialsResponse(credential);
    }


    private UserCredentialResponse toCredentialsResponse(UserCredential credential) {
        UserCredentialResponse.UserCredentialResponseBuilder builder = UserCredentialResponse.builder();
        builder.id(credential.getId());
        builder.userId(credential.getUser().getId());
        builder.jiraUsername(credential.getUsername());
        builder.createdAt(credential.getCreatedAt());
        builder.expiresAt(credential.getExpiresAt());
        return builder.build();
    }


    @Override
    public boolean existsByJiraUsername(String jiraUsername) {
        return credentialRepository.existsByUsername(jiraUsername);
    }
    
    @Override
   public UserCredential findByJiraUsername(String jiraUsername){
        Optional<UserCredential> userCredential = credentialRepository.findByUsername(jiraUsername);
        UserCredential credential = null;
        if(userCredential.isPresent()){ credential = userCredential.get(); }
        return credential;
    }

}
