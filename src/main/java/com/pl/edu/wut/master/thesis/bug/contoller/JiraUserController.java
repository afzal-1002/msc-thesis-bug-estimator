package com.pl.edu.wut.master.thesis.bug.contoller;

import com.pl.edu.wut.master.thesis.bug.model.user.JiraUserResponse;
import com.pl.edu.wut.master.thesis.bug.model.user.UserCredential;
import com.pl.edu.wut.master.thesis.bug.service.JiraUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jira/users")
@RequiredArgsConstructor
public class JiraUserController {

    private final JiraUserService jiraUserService;

    /** Who am I in Jira? */
    @GetMapping("/me")
    public JiraUserResponse getMyself() {
        return jiraUserService.getUserDetailsFromJira();
    }

    /** Lookup a stored credential by Jira username */
    @GetMapping("/{username}/credential")
    public UserCredential getUserCredential(@PathVariable String username) {
        return jiraUserService.getUserByUsername(username);
    }
}