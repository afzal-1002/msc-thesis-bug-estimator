package com.pl.edu.wut.master.thesis.bug.service;

import com.pl.edu.wut.master.thesis.bug.dto.issue.request.*;
import com.pl.edu.wut.master.thesis.bug.dto.issue.response.*;
import com.pl.edu.wut.master.thesis.bug.model.issue.CreateIssueRequest;
import com.pl.edu.wut.master.thesis.bug.model.issue.IssueRecord;
import com.pl.edu.wut.master.thesis.bug.model.issue.IssueSearchResult;
import com.pl.edu.wut.master.thesis.bug.model.issuetype.IssueTypeSummary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JiraIssueService {
    // existing
    JiraSearchResult            searchIssues(int startAt);
    IssueResponseSummary            createIssue(CreateIssueRequest request);
    List<IssueTypeSummary>      listAllIssueTypes();
    List<IssueTypeSummary>      getAllJiraIssue();
    IssueResponse               getIssueByKey(String issueKey);
    IssueRecord                 getIssueByKeyIssuesSummaryResponse(String issueKey);
    IssueSearchResult           listIssuesForProject(String projectKey);
    IssueSearchResult           fetchAllIssuesCurrentProject();

    // ─── NEW ENDPOINTS ────────────────────────────────────────────────────────
    /** Bulk create or update. */
    BulkIssueResponse         bulkIssue(BulkIssueRequest request);

    /** Bulk fetch many issues by ID/key. */
    BulkFetchResponse         bulkFetchIssues(BulkFetchRequest request);

    /** Global create‐meta (all projects & types). */
    CreateMetaResponse        getCreateMeta();

    /** Create‐meta for a single project. */
    CreateMetaResponse        getCreateMetaForProject(String projectIdOrKey);

    /** Fully update an issue (fields + history metadata). */
    IssueResponse updateIssue(String issueIdOrKey, CreateIssueRequest request);

    /** Delete an issue. */
    void                       deleteIssue(String issueIdOrKey);

    /** Assign (or reassign) an issue to a user. */
    void                       assignIssue(String issueIdOrKey, String accountId);

    /** Get an issue’s changelog. */
    ChangelogResponse          getChangelog(String issueIdOrKey);

    /** List specific changelog entries by ID. */
    ChangelogListResponse      listChangelog(String issueIdOrKey, ChangelogListRequest request);

    /** Archive a batch of issues. */
    ArchiveResponse            archiveIssues(ArchiveRequest request);

    /** Unarchive a batch of issues. */
    ArchiveResponse            unarchiveIssues(ArchiveRequest request);
}
