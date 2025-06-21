
package com.pl.edu.wut.master.thesis.bug.service;

import com.pl.edu.wut.master.thesis.bug.dto.request.IssueRequest;
import com.pl.edu.wut.master.thesis.bug.dto.response.CommentResponse;
import com.pl.edu.wut.master.thesis.bug.dto.response.IssueResponse;

import java.util.List;

public interface IssueService {
    List<IssueResponse> getAllIssues();
    IssueResponse getIssueById(Long issueId);
    IssueResponse getIssueByKey(String issueKey);
    List<IssueResponse> getIssuesForProject(String projectKey);
    IssueResponse createIssue(IssueRequest request);
    IssueResponse updateIssue(String issueKey, IssueRequest request);
    void deleteIssue(String issueKey);
    IssueResponse getIssueForEstimation(String issueKey);
    List<Object> getIssueChangelog(String issueKey);
    Object getIssueEditMeta(String issueKey);
    List<Object> getIssueTransitions(String issueKey);
    void transitionIssue(String issueKey, Object transitionRequest);
    void assignIssue(String issueKey, Object assigneeRequest);
    List<IssueResponse> searchIssues(String jql);

    CommentResponse addEstimationComment(String issueKey, String body);



}
