//// src/main/java/com/pl/edu/wut/master/thesis/bug/service/JiraClientService.java
//package com.pl.edu.wut.master.thesis.bug.service;
//
//import com.pl.edu.wut.master.thesis.bug.dto.comment.CreateCommentRequest;
//import com.pl.edu.wut.master.thesis.bug.dto.comment.JiraCommentResponse;
//import com.pl.edu.wut.master.thesis.bug.dto.issue.request.IssueRequest;
//import com.pl.edu.wut.master.thesis.bug.dto.issue.response.JiraSearchResult;
//import com.pl.edu.wut.master.thesis.bug.dto.issue.type.IssueTypeSummary;
//import com.pl.edu.wut.master.thesis.bug.dto.project.CreateProjectRequest;
//import com.pl.edu.wut.master.thesis.bug.dto.project.CreateProjectResponse;
//import com.pl.edu.wut.master.thesis.bug.dto.project.ProjectDetailsSummary;
//import com.pl.edu.wut.master.thesis.bug.dto.project.ProjectSummary;
//import com.pl.edu.wut.master.thesis.bug.model.user.JiraUserResponse;
//import com.pl.edu.wut.master.thesis.bug.model.common.CommentWrapper;
//import com.pl.edu.wut.master.thesis.bug.model.comment.JiraComment;
//import com.pl.edu.wut.master.thesis.bug.model.issue.jira.create.IssueResponse;
//import com.pl.edu.wut.master.thesis.bug.model.issue.jira.create.IssueResponse;
//import com.pl.edu.wut.master.thesis.bug.model.user.User;
//import com.pl.edu.wut.master.thesis.bug.model.user.UserCredential;
//
//import java.util.List;
//import java.util.Map;
//
//public interface JiraClientService {
//
//
//
//    // ─── Project “remote” calls ────────────────────────────────
//// ─── Remote Jira “Project” APIs ─────────────────────────
//    ProjectDetailsSummary         getProjectDetailsByKeyOrId(String projectKeyOrId);
//    List<String>               listProjectKeys();
//    Map<String, Object>        validateKey(String key);
//    String                     generateValidKey(String desiredKey);
//    String                     generateValidName(String desiredName);
//    CreateProjectResponse  createProjectInJira(CreateProjectRequest request, User currentUser);
//    List<ProjectSummary>       getAllProjectsFromJira();
//
//
//    JiraUserResponse getUserDetailsFromJira();
//    UserCredential getJiraUserDetailsByUsername(String jiraUsername);
//    JiraSearchResult getAllIssues(int startAt);
//
//    IssueResponse createIssueInJira(IssueRequest request);
//
//    // Issue methods
//
//    List<IssueResponse> getAllIssuesForCurrentProjectFromJira();
//
//    List<IssueTypeSummary> getAllIssueTypesFromJira();
//
//    List<IssueTypeSummary> getIssueTypesForCurrentProject();
//
//    IssueResponse getIssueByIssueKey(String issueKey);
//
//    // Comment methods
//
//    CommentWrapper getRawIssueComments(String issueKey);
//
////    List<JiraComment> getIssueComments(String issueKey);
//
//    JiraComment postIssueComment(String issueKey, String body);
//
//    JiraComment updateIssueComment(String issueKey, String commentId, String body);
//
//    void deleteIssueComment(String issueKey, String commentId);
//
//    /**
//     * Fetch all issues in any project by key.
//     */
//    List<IssueResponse> getAllIssuesForProject(String projectKey);
//
//    /**
//     * Convenience: pull out just the keys from that same call.
//     */
//    default List<String> getIssueKeysForProject(String projectKey) {
//        return getAllIssuesForProject(projectKey)
//                .stream()
//                .map(IssueResponse::getKey)
//                .toList();
//    }
//
////    JiraCommentResponse createComment(String issueKey, CreateCommentRequest request);
//}
