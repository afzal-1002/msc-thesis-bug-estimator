package com.pl.edu.wut.master.thesis.bug.contoller;

import com.pl.edu.wut.master.thesis.bug.model.issue.CreateIssueRequest;
import com.pl.edu.wut.master.thesis.bug.dto.issue.response.IssueResponse;
import com.pl.edu.wut.master.thesis.bug.model.issue.Issue;
import com.pl.edu.wut.master.thesis.bug.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wut/issues")
@RequiredArgsConstructor
public class IssueController {

    private final IssueService issueService;

    /** 1. Create a new issue in Jira and save locally */
    @PostMapping("")
    public ResponseEntity<IssueResponse> createIssue(@RequestBody CreateIssueRequest request) {
        IssueResponse created = issueService.createIssue(request);
        return ResponseEntity.ok(created);
    }

    /** 2. List *all* issues (sync first, then fetch local) */
    @GetMapping("")
    public ResponseEntity<List<IssueResponse>> listAllIssues() {
        List<IssueResponse> list = issueService.listAllIssues();
        return ResponseEntity.ok(list);
    }

    /** 3. Get (and upsert) one issue by its Jira key */
    @GetMapping("/issues/{key}")
    public ResponseEntity<IssueResponse> getIssueByKey(@PathVariable("key") String issueKey) {
        IssueResponse resp = issueService.getIssueByKey(issueKey);
        return ResponseEntity.ok(resp);
    }

    /** 4. List issues in Jira for a given project key (and save locally) */
    @GetMapping("/projects/{projectKey}/issues")
    public ResponseEntity<List<IssueResponse>> listIssuesForProject(@PathVariable String projectKey) {
        List<IssueResponse> listedIssuesForProject = issueService.listIssuesForProject(projectKey);
        return ResponseEntity.ok(listedIssuesForProject);
    }

    /** 5. Update an existing issue in Jira (and local DB) */
    @PutMapping("/issues/{key}")
    public ResponseEntity<IssueResponse> updateIssue( @PathVariable("key") String issueKey,
                                                      @RequestBody CreateIssueRequest request) {
        IssueResponse updated = issueService.updateIssue(issueKey, request);
        return ResponseEntity.ok(updated);
    }

    /** 6. Force a full sync of all Jira issues into the local DB */
    @PostMapping("/sync")
    public ResponseEntity<Void> synchronizeAllIssues() {
        issueService.synchronizeAllIssues();
        return ResponseEntity.noContent().build();
    }

    /** 7. Find all (local) issues for a numeric project ID (upsert from Jira) */
    @GetMapping("/projects/{projectId}/issues/local")
    public ResponseEntity<List<Issue>> findByProjectId(@PathVariable Long projectId) {
        List<Issue> list = issueService.findByProjectId(projectId);
        return ResponseEntity.ok(list);
    }
}
