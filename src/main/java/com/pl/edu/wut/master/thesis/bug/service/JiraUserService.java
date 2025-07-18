package com.pl.edu.wut.master.thesis.bug.service;

import com.pl.edu.wut.master.thesis.bug.model.user.JiraUserResponse;
import com.pl.edu.wut.master.thesis.bug.model.user.UserCredential;

public interface JiraUserService {

    /** Fetch details about the current authenticated Jira user. */
    JiraUserResponse getUserDetailsFromJira();

    /** Lookup a Jira user by their Jira username. */
    UserCredential getUserByUsername(String jiraUsername);


}
