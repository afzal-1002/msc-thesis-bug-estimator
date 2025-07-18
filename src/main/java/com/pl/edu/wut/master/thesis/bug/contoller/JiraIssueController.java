package com.pl.edu.wut.master.thesis.bug.contoller;

import com.pl.edu.wut.master.thesis.bug.dto.issue.request.*;
import com.pl.edu.wut.master.thesis.bug.dto.issue.response.*;
import com.pl.edu.wut.master.thesis.bug.model.issue.CreateIssueRequest;
import com.pl.edu.wut.master.thesis.bug.model.issue.IssueRecord;
import com.pl.edu.wut.master.thesis.bug.model.issue.IssueSearchResult;
import com.pl.edu.wut.master.thesis.bug.model.issuetype.IssueTypeSummary;
import com.pl.edu.wut.master.thesis.bug.service.JiraIssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jira/issues")
@RequiredArgsConstructor
public class JiraIssueController {

    private final JiraIssueService jiraIssueService;

    // ─── Search & CRUD ──────────────────────────────────────────────────────────

    @GetMapping("/search")
    public JiraSearchResult searchIssues(@RequestParam(defaultValue = "0") int startAt) {
        return jiraIssueService.searchIssues(startAt);
    }

    @PostMapping("")
    public IssueResponseSummary createIssue(@RequestBody CreateIssueRequest create) {
        return jiraIssueService.createIssue(create);
    }

    @GetMapping("/{issueKey}")
    public IssueResponse getIssueByKey(@PathVariable String issueKey) {
        return jiraIssueService.getIssueByKey(issueKey);
    }

    @GetMapping("/{issueKey}/summary")
    public IssueRecord getIssueByKeyIssuesSummaryResponse(@PathVariable String issueKey) {
        return jiraIssueService.getIssueByKeyIssuesSummaryResponse(issueKey);
    }

    @PutMapping("/{issueIdOrKey}")
    public IssueResponse updateIssue( @PathVariable String issueIdOrKey, @RequestBody CreateIssueRequest request)
    {
        return jiraIssueService.updateIssue(issueIdOrKey, request);
    }

    @DeleteMapping("/{issueIdOrKey}")
    public ResponseEntity<Void> deleteIssue(@PathVariable String issueIdOrKey) {
        jiraIssueService.deleteIssue(issueIdOrKey);
        return ResponseEntity.noContent().build();
    }

    // ─── Issue‐Type Helpers ────────────────────────────────────────────────────

    @GetMapping("/types")
    public List<IssueTypeSummary> listAllIssueTypes() {
        return jiraIssueService.listAllIssueTypes();
    }

    @GetMapping("/types/project")
    public List<IssueTypeSummary> getAllIssueTypesForCurrentProject() {
        return jiraIssueService.getAllJiraIssue();
    }


    // ─── Project‐Scoped Issue Lists ─────────────────────────────────────────────

    @GetMapping("/project/{projectKey}")
    public IssueSearchResult listIssuesForProject(@PathVariable String projectKey) {
        return jiraIssueService.listIssuesForProject(projectKey);
    }

    @GetMapping("/project/current/fetch")
    public IssueSearchResult fetchAllIssuesCurrentProject() {
        return jiraIssueService.fetchAllIssuesCurrentProject();
    }

    // ─── Bulk Operations ───────────────────────────────────────────────────────

    @PostMapping("/bulk")
    public BulkIssueResponse bulkIssue(@RequestBody BulkIssueRequest request) {
        return jiraIssueService.bulkIssue(request);
    }

    @PostMapping("/bulkfetch")
    public BulkFetchResponse bulkFetchIssues(@RequestBody BulkFetchRequest request) {
        return jiraIssueService.bulkFetchIssues(request);
    }

    // ─── Create‐Meta ───────────────────────────────────────────────────────────

    @GetMapping("/createmeta")
    public CreateMetaResponse getCreateMeta() {
        return jiraIssueService.getCreateMeta();
    }

    @GetMapping("/createmeta/{projectIdOrKey}/issuetypes")
    public CreateMetaResponse getCreateMetaForProject( @PathVariable String projectIdOrKey)
    {
        return jiraIssueService.getCreateMetaForProject(projectIdOrKey);
    }

    // ─── Assignment & Changelog ────────────────────────────────────────────────

    @PutMapping("/{issueIdOrKey}/assignee")
    public ResponseEntity<Void> assignIssue( @PathVariable String issueIdOrKey,
                                             @RequestBody AssigneeRequest request )
    {
        jiraIssueService.assignIssue(issueIdOrKey, request.getAccountId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{issueIdOrKey}/changelog")
    public ChangelogResponse getChangelog(@PathVariable String issueIdOrKey) {
        return jiraIssueService.getChangelog(issueIdOrKey);
    }

    @PostMapping("/{issueIdOrKey}/changelog/list")
    public ChangelogListResponse listChangelog( @PathVariable String issueIdOrKey,
                                                @RequestBody ChangelogListRequest request )
    {
        return jiraIssueService.listChangelog(issueIdOrKey, request);
    }

    // ─── Archive / Unarchive ───────────────────────────────────────────────────

    @PutMapping("/archive")
    public ArchiveResponse archiveIssues(@RequestBody ArchiveRequest request) {
        return jiraIssueService.archiveIssues(request);
    }

    @PutMapping("/unarchive")
    public ArchiveResponse unarchiveIssues(@RequestBody ArchiveRequest request) {
        return jiraIssueService.unarchiveIssues(request);
    }
}

