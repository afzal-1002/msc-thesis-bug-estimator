package com.pl.edu.wut.master.thesis.bug.service.implementation;

import com.pl.edu.wut.master.thesis.bug.configuration.JiraClientConfiguration;
import com.pl.edu.wut.master.thesis.bug.dto.issue.request.*;
import com.pl.edu.wut.master.thesis.bug.dto.issue.response.*;
import com.pl.edu.wut.master.thesis.bug.model.issue.CreateIssueRequest;
import com.pl.edu.wut.master.thesis.bug.model.issue.IssueRecord;
import com.pl.edu.wut.master.thesis.bug.model.issue.IssueSearchResult;
import com.pl.edu.wut.master.thesis.bug.model.issuetype.IssueTypeSummary;
import com.pl.edu.wut.master.thesis.bug.service.JiraIssueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class JiraIssueServiceImplementation implements JiraIssueService {

    private final JiraClientConfiguration jiraClientConfiguration;

    @Override
    public JiraSearchResult searchIssues(int startAt) {
        return jiraClientConfiguration
                .get("/issue/search?startAt=" + startAt, JiraSearchResult.class);
    }

    /**
     * Create a new issue in Jira.
     * Returns only { id, key, self } in a CreateNewIssueResponse.
     */
    @Override
    public IssueResponseSummary createIssue(CreateIssueRequest request) {
        return jiraClientConfiguration.post("/issue", request, IssueResponseSummary.class);
    }

//    @Override
//    public IssueResponse createIssueByIdOrKey(CreateIssueRequest incoming) {
//        // 1) Ensure we got a fields block
//        CreateIssueFields fields = requireNonNull(incoming.getFields(),
//                "Request must include a top-level ‘fields’ object");
//
//        // 2) Project: must have id or key
//        ProjectReference proj = incoming.getFields().getProject();
//        if (proj == null || (isBlank(proj.getId()) && isBlank(proj.getKey()))) {
//            throw new IllegalArgumentException("fields.project must include at least ‘id’ or ‘key’");
//        }
//
//        // 3) IssueType: must have id or name
//        IssueTypeReference issueType = fields.getIssuetype();
//        if (issueType == null || (isBlank(issueType.getId()) && isBlank(issueType.getName()))) {
//            throw new IllegalArgumentException("fields.issueType must include at least ‘id’ or ‘name’");
//        }
//
//        // 4) Summary: required
//        if (fields.getSummary() == null || fields.getSummary().isBlank()) {
//            throw new IllegalArgumentException("fields.summary is required");
//        }
//
//        // 5) Assignee: must have id or username
//        AssigneeReference a = fields.getAssignee();
//        if (a == null ||
//                (isBlank(a.getId()) && isBlank(a.getUsername()))) {
//            throw new IllegalArgumentException(
//                    "fields.assignee must include at least ‘id’ or ‘username’");
//        }
//
//        // 6) Everything else (duedate, description, labels, etc.) is passed through
//        log.info("Creating Jira issue with project={} issuetype={} summary={}",
//                proj.getId()!=null?proj.getId():proj.getKey(),
//                issueType.getId()!=null?issueType.getId():issueType.getName(),
//                fields.getSummary());
//
//        // 7) Delegate: this will serialize exactly what the client sent
//        return jiraClientConfiguration
//                .post("/issue", incoming, IssueResponse.class);
//    }





    @Override
    public List<IssueTypeSummary> listAllIssueTypes() {
        return List.of(jiraClientConfiguration.get("/issuetype", IssueTypeSummary[].class));
    }

    @Override
    public List<IssueTypeSummary> getAllJiraIssue() {
        return List.of(jiraClientConfiguration.get("/issuetype/project?projectId=" +
                        jiraClientConfiguration.loadSessionContext().getProjectId(),
                IssueTypeSummary[].class));
    }

    @Override
    public IssueResponse getIssueByKey(String issueKey) {
        return jiraClientConfiguration.get("/issue/" + issueKey, IssueResponse.class);
    }

    @Override
    public IssueRecord getIssueByKeyIssuesSummaryResponse(String issueKey){
        return jiraClientConfiguration.get("/issue/" + issueKey, IssueRecord.class);
    }

    @Override
    public IssueSearchResult listIssuesForProject(String projectKey) {
        return jiraClientConfiguration.get("/search?jql=project=" + projectKey, IssueSearchResult.class);
    }

    @Override
    public IssueSearchResult fetchAllIssuesCurrentProject() {
        return jiraClientConfiguration.get("/search?jql=project=" +
                        jiraClientConfiguration.loadSessionContext().getProjectKey(), IssueSearchResult.class);
    }

    // ─── NEW IMPLEMENTATIONS ───────────────────────────────────────────────────

    @Override
    public BulkIssueResponse bulkIssue(BulkIssueRequest request) {
        return jiraClientConfiguration.post("/issue/bulk",
                request, BulkIssueResponse.class);
    }

    @Override
    public BulkFetchResponse bulkFetchIssues(BulkFetchRequest request) {
        return jiraClientConfiguration
                .post("/issue/bulkfetch", request, BulkFetchResponse.class);
    }

    @Override
    public CreateMetaResponse getCreateMeta() {
        return jiraClientConfiguration
                .get("/issue/createmeta", CreateMetaResponse.class);
    }

    @Override
    public CreateMetaResponse getCreateMetaForProject(String projectIdOrKey) {
        return jiraClientConfiguration
                .get("/issue/createmeta/" + projectIdOrKey + "/issuetypes",
                        CreateMetaResponse.class);
    }

    @Override
    @Transactional
    public IssueResponse updateIssue(String issueIdOrKey, CreateIssueRequest request) {
        return jiraClientConfiguration.put("/issue/" + issueIdOrKey, request, IssueResponse.class);
    }

    @Override
    @Transactional
    public void deleteIssue(String issueIdOrKey) {
        jiraClientConfiguration
                .delete("/issue/" + issueIdOrKey, Void.class);
    }

    @Override
    @Transactional
    public void assignIssue(String issueIdOrKey, String accountId) {
        // build a minimal payload DTO { accountId: "..." }
        AssigneeRequest dto = new AssigneeRequest(accountId);
        jiraClientConfiguration
                .put("/issue/" + issueIdOrKey + "/assignee", dto, Void.class);
    }

    @Override
    public ChangelogResponse getChangelog(String issueIdOrKey) {
        return jiraClientConfiguration
                .get("/issue/" + issueIdOrKey + "/changelog", ChangelogResponse.class);
    }

    @Override
    public ChangelogListResponse listChangelog(String issueIdOrKey, ChangelogListRequest request) {
        return jiraClientConfiguration
                .post("/issue/" + issueIdOrKey + "/changelog/list", request, ChangelogListResponse.class);
    }

    @Override
    public ArchiveResponse archiveIssues(ArchiveRequest request) {
        return jiraClientConfiguration
                .put("/issue/archive", request, ArchiveResponse.class);
    }

    @Override
    public ArchiveResponse unarchiveIssues(ArchiveRequest request) {
        return jiraClientConfiguration
                .put("/issue/unarchive", request, ArchiveResponse.class);
    }

    private <T> T requireNonNull(T obj, String message) {
        if (obj == null) throw new IllegalArgumentException(message);
        return obj;
    }


    private boolean isBlank(String string) { return string == null || string.isBlank(); }
}
