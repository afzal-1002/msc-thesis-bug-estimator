package com.pl.edu.wut.master.thesis.bug.service;

import com.pl.edu.wut.master.thesis.bug.dto.issue.response.IssueResponse;
import com.pl.edu.wut.master.thesis.bug.model.issue.CreateIssueRequest;
import com.pl.edu.wut.master.thesis.bug.model.issue.Issue;

import java.util.List;

public interface IssueService {
    // ─── Jira-remote calls

    IssueResponse createIssue(CreateIssueRequest request);
    List<IssueResponse> listAllIssues();
    IssueResponse getIssueByKey(String issueKey);
    List<IssueResponse> listIssuesForProject(String projectKey);
    IssueResponse updateIssue(String issueKey, CreateIssueRequest request);
    void synchronizeAllIssues();
    List<Issue> findByProjectId(Long projectId);

//
//    IssueResponse          updateIssue(String issueKey, IssueUpdateRequest update);
//    void                   deleteIssue(String issueKey);
//    JiraSearchResult       searchIssues(String jql, int startAt);
//    TransitionResponse     transitionIssue(String issueKey, TransitionRequest transition);
//
//    // ─── Session-managed “selected” issue
//    Issue                  selectIssue(String issueKey);
//    String                 getSelectedIssue();
//    void                   deselectIssue();
//    boolean                validateIssueKey(String issueKey);
//
//    // ─── Local DB lookups
//    IssueResponse          findLocalIssue(String issueKey);
//    List<IssueResponse>    findLocalByProject(String projectKey);
//    List<IssueResponse>    findLocalByAssignee(String accountId);
//
//    // ─── Basic JPA CRUD
//    List<Issue>            getAllIssues();
//    Optional<Issue>        getIssueById(Long id);
//    Issue                  createLocalIssue(Issue issue);
//    Issue                  updateLocalIssue(Long id, Issue issue);
//    void                   deleteLocalIssue(Long id);
//
//    // ─── DTO-mapping helper
//    IssueDto               getIssueDetails(String issueKey);
//    Optional<Issue>        getIssueByKeyLocal(String issueKey);
}
