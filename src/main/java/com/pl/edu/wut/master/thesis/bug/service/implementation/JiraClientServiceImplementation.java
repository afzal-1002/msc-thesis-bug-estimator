//package com.pl.edu.wut.master.thesis.bug.service.implementation;
//
//import com.pl.edu.wut.master.thesis.bug.configuration.JiraClientConfiguration;
//import com.pl.edu.wut.master.thesis.bug.dto.comment.CreateCommentRequest;
//import com.pl.edu.wut.master.thesis.bug.dto.comment.JiraCommentResponse;
//import com.pl.edu.wut.master.thesis.bug.dto.issue.request.IssueRequest;
//import com.pl.edu.wut.master.thesis.bug.dto.issue.response.JiraIssueListResponse;
//import com.pl.edu.wut.master.thesis.bug.dto.issue.response.JiraIssueSearchResult;
//import com.pl.edu.wut.master.thesis.bug.dto.issue.response.JiraSearchResult;
//import com.pl.edu.wut.master.thesis.bug.dto.issue.type.IssueTypeSummary;
//import com.pl.edu.wut.master.thesis.bug.dto.project.CreateProjectRequest;
//import com.pl.edu.wut.master.thesis.bug.dto.project.CreateProjectResponse;
//import com.pl.edu.wut.master.thesis.bug.dto.project.ProjectDetailsSummary;
//import com.pl.edu.wut.master.thesis.bug.dto.project.ProjectSummary;
//import com.pl.edu.wut.master.thesis.bug.model.user.JiraUserResponse;
//import com.pl.edu.wut.master.thesis.bug.dto.user.usersession.SessionContext;
//import com.pl.edu.wut.master.thesis.bug.exception.CustomException;
//import com.pl.edu.wut.master.thesis.bug.exception.DuplicateProjectNameException;
//import com.pl.edu.wut.master.thesis.bug.exception.ResourceNotFoundException;
//import com.pl.edu.wut.master.thesis.bug.exception.UserNotAuthorizedException;
//import com.pl.edu.wut.master.thesis.bug.mapper.JiraIssueMapper;
//import com.pl.edu.wut.master.thesis.bug.model.comment.JiraComment;
//import com.pl.edu.wut.master.thesis.bug.model.common.CommentWrapper;
//import com.pl.edu.wut.master.thesis.bug.model.issue.jira.create.IssueResponse;
//import com.pl.edu.wut.master.thesis.bug.model.issue.jira.create.IssueResponse;
//import com.pl.edu.wut.master.thesis.bug.model.user.User;
//import com.pl.edu.wut.master.thesis.bug.model.user.UserCredential;
//import com.pl.edu.wut.master.thesis.bug.repository.UserCredentialRepository;
//import com.pl.edu.wut.master.thesis.bug.service.JiraClientService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.server.ResponseStatusException;
//import org.springframework.web.util.UriComponentsBuilder;
//import org.springframework.web.util.UriUtils;
//
//import java.nio.charset.StandardCharsets;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class JiraClientServiceImplementation implements JiraClientService {
//
//    private final JiraClientConfiguration jiraClientConfiguration;
//    private final UserCredentialRepository credentialRepository;
//    private final JiraIssueMapper jiraIssueMapper;
//
//    @Override
//    public ProjectDetailsSummary getProjectDetailsByKeyOrId(String projectKeyOrId) {
//        try {
//            return jiraClientConfiguration.get(
//                    "/project/" + projectKeyOrId,
//                    ProjectDetailsSummary.class
//            );
//        } catch (HttpClientErrorException.NotFound ex) {
//            throw new ResourceNotFoundException("Project not found: " + projectKeyOrId);
//        } catch (HttpClientErrorException.Forbidden ex) {
//            throw new UserNotAuthorizedException("Invalid Jira credentials");
//        }
//    }
//
//    @Override
//    public JiraUserResponse getUserDetailsFromJira() {
//        try {
//            return jiraClientConfiguration.get("/myself", JiraUserResponse.class);
//        } catch (HttpClientErrorException.NotFound ex) {
//            throw new ResourceNotFoundException("User not found");
//        } catch (HttpClientErrorException.Forbidden ex) {
//            throw new UserNotAuthorizedException("Invalid Jira credentials");
//        }
//    }
//
//    @Override
//    public UserCredential getJiraUserDetailsByUsername(String jiraUsername) {
//        return credentialRepository.findByUsername(jiraUsername).orElse(null);
//    }
//
//    @Override
//    public JiraSearchResult getAllIssues(int startAt) {
//        String jql = "";
//        int max = 1000;
//        String fields = String.join(",", List.of(
//                "key","summary","issuetype","priorityEnum","description",
//                "environment","project","assignee","reporter",
//                "timespent","duedate"
//        ));
//        String path = UriComponentsBuilder
//                .fromPath("/search")
//                .queryParam("jql", jql)
//                .queryParam("startAt", startAt)
//                .queryParam("maxResults", max)
//                .queryParam("fields", fields)
//                .toUriString();
//
//        return jiraClientConfiguration.get(path, JiraSearchResult.class);
//    }
//
//    @Override
//    @SuppressWarnings("unchecked")
//    public List<String> listProjectKeys() {
//        List<Map<String,Object>> list = jiraClientConfiguration.get("/project", List.class);
//        return list.stream().map(m -> (String)m.get("key")).toList();
//    }
//
//    @Override
//    @SuppressWarnings("unchecked")
//    public Map<String,Object> validateKey(String key) {
//        String path = UriComponentsBuilder
//                .fromPath("/projectvalidate/key")
//                .queryParam("key", key)
//                .toUriString();
//        return jiraClientConfiguration.get(path, Map.class);
//    }
//
//    @Override
//    public String generateValidKey(String desiredKey) {
//        String raw = jiraClientConfiguration.get(
//                UriComponentsBuilder
//                        .fromPath("/projectvalidate/validProjectKey")
//                        .queryParam("key", desiredKey)
//                        .toUriString(),
//                String.class
//        );
//        return raw.replaceAll("^\"|\"$", "");
//    }
//
//    @Override
//    public String generateValidName(String desiredName) {
//        String raw = jiraClientConfiguration.get(
//                UriComponentsBuilder
//                        .fromPath("/projectvalidate/validProjectName")
//                        .queryParam("name", desiredName)
//                        .toUriString(),
//                String.class
//        );
//        return raw.replaceAll("^\"|\"$", "");
//    }
//
//    @Override
//    public CreateProjectResponse createProjectInJira(CreateProjectRequest request, User currentUser) {
//        String key = request.getKey();
//        String name = request.getName();
//        if (!key.matches("^[A-Z]{2,10}$")) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid project key format");
//        }
//        if (listProjectKeys().contains(key)) {
//            throw new ResponseStatusException(HttpStatus.CONFLICT, "Project key already exists");
//        }
//        Map<String,Object> val = validateKey(key);
//        if (!((List<?>)val.get("errorMessages")).isEmpty()
//                || ((Map<?,?>)val.get("errors")).containsKey("projectKey")) {
//            throw new ResponseStatusException(
//                    HttpStatus.BAD_REQUEST,
//                    "Jira validation failed for key '" + key + "'"
//            );
//        }
//        String fallback = generateValidName(name);
//        if (!fallback.equals(name)) {
//            throw new DuplicateProjectNameException(
//                    "Project name already exists", HttpStatus.CONFLICT
//            );
//        }
//        return jiraClientConfiguration.post("/project", request, CreateProjectResponse.class);
//    }
//
//    @Override
//    public List<ProjectSummary> getAllProjectsFromJira() {
//        ProjectSummary[] arr = jiraClientConfiguration.get("/project", ProjectSummary[].class);
//        return arr == null ? Collections.emptyList() : Arrays.asList(arr);
//    }
//
//
//
//    @Override
//    public List<IssueTypeSummary> getAllIssueTypesFromJira() {
//        IssueTypeSummary[] arr = jiraClientConfiguration.get("/issuetype", IssueTypeSummary[].class);
//        return arr == null ? Collections.emptyList() : Arrays.asList(arr);
//    }
//
//    @Override
//    public List<IssueTypeSummary> getIssueTypesForCurrentProject() {
//        SessionContext context = jiraClientConfiguration.loadSessionContext();
//        String path = UriComponentsBuilder
//                .fromPath("/issuetype/project")
//                .queryParam("projectId", context.getProjectId())
//                .toUriString();
//
//        IssueTypeSummary[] issueTypeList =
//                jiraClientConfiguration.get(path, IssueTypeSummary[].class);
//
//        if (issueTypeList == null || issueTypeList.length == 0) {
//            path = UriComponentsBuilder
//                    .fromPath("/issuetype/project")
//                    .queryParam("projectKey", context.getProjectKey())
//                    .toUriString();
//            issueTypeList = jiraClientConfiguration.get(path, IssueTypeSummary[].class);
//        }
//
//        return issueTypeList == null
//                ? Collections.emptyList()
//                : Arrays.asList(issueTypeList);
//    }
//
//    @Override
//    public IssueResponse getIssueByIssueKey(String issueKey) {
//        String safeKey = UriUtils.encodePath(issueKey, StandardCharsets.UTF_8);
//        // 1) Fetch raw JSON → binding model
//        IssueResponse raw = jiraClientConfiguration.get("/issue/" + safeKey, IssueResponse.class);
//
//        // 2) Map to your flattened DTO
//        return jiraIssueMapper.IssueResponse(raw);
//    }
//
//    @Override
//    public CommentWrapper getRawIssueComments(String issueKey) {
//        String safe = UriUtils.encodePath(issueKey, StandardCharsets.UTF_8);
//        return jiraClientConfiguration.get("/issue/" + safe + "/comment", CommentWrapper.class);
//    }
//
////    @Override
////    public List<JiraComment> getIssueComments(String issueKey) {
////        CommentWrapper wrapper = getRawIssueComments(issueKey);
////        return wrapper != null ? wrapper.getComments() : Collections.emptyList();
////    }
//
//    @Override
//    public JiraComment postIssueComment(String issueKey, String body) {
//        String safe = UriUtils.encodePath(issueKey, StandardCharsets.UTF_8);
//        var payload = Collections.singletonMap("body", body);
//        return jiraClientConfiguration.post("/issue/" + safe + "/comment", payload, JiraComment.class);
//    }
//
//    @Override
//    public JiraComment updateIssueComment(String issueKey, String commentId, String body) {
//        String safe = UriUtils.encodePath(issueKey, StandardCharsets.UTF_8);
//        var payload = Collections.singletonMap("body", body);
//        return jiraClientConfiguration.put(
//                "/issue/" + safe + "/comment/" + commentId,
//                payload,
//                JiraComment.class
//        );
//    }
//
//    @Override
//    public void deleteIssueComment(String issueKey, String commentId) {
//        String safe = UriUtils.encodePath(issueKey, StandardCharsets.UTF_8);
//        jiraClientConfiguration.delete("/issue/" + safe + "/comment/" + commentId, Void.class);
//    }
//
//    @Override
//    public List<IssueResponse> getAllIssuesForCurrentProjectFromJira() {
//        // 1) load current project key from session
//        SessionContext context = jiraClientConfiguration.loadSessionContext();
//        String projectKey = context.getProjectKey();
//
//        // 2) build JQL without quotes or spaces, and without URL-encoding the '='
//        String jql  = "project=" + projectKey;
//        String path = "/search?jql=" + jql + "&maxResults=1000";
//
//        // 3) call Jira
//        JiraIssueSearchResult jiraIssueSearchResult = jiraClientConfiguration.get(path, JiraIssueSearchResult.class);
//
//        return jiraIssueSearchResult.getIssues();
//    }
//
//    @Override
//    public List<IssueResponse> getAllIssuesForProject(String projectKey) {
//        // same logic, but taking the projectKey as a parameter
//        String jql  = "project=" + projectKey;
//        String path = "/search?jql=" + jql + "&maxResults=1000";
//
//        JiraIssueSearchResult wrapper =
//                jiraClientConfiguration.get(path, JiraIssueSearchResult.class);
//        return wrapper.getIssues();
//    }
//
//    @Override
//    public List<String> getIssueKeysForProject(String projectKey) throws CustomException {
//        try {
//            log.debug("Fetching issue keys for project: {}", projectKey);
//
//            // Validate project key format
//            if (projectKey == null || !projectKey.matches("[A-Za-z]{2,}")) {
//                throw new CustomException("Invalid project key format", HttpStatus.BAD_REQUEST);
//            }
//
//            String jql = String.format("project=%s&fields=key&maxResults=1000", projectKey);
//            String path = String.format("/search?jql=%s", jql);
//
//            JiraIssueListResponse response = jiraClientConfiguration.get(path, JiraIssueListResponse.class);
//
//            if (response == null || response.getIssues() == null) {
//                log.warn("No issues found for project: {}", projectKey);
//                throw new CustomException("No issues found for project: " + projectKey, HttpStatus.NOT_FOUND);
//            }
//
//            return response.getIssues().stream()
//                    .map(IssueResponse::getKey)
//                    .collect(Collectors.toList());
//
//        } catch (CustomException e) {
//            throw e;
//        } catch (Exception e) {
//            log.error("Failed to get issue keys for project: {}", projectKey, e);
//            throw new CustomException("Failed to get issue keys for project: " + projectKey,
//                    HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
////    @Override
////    public JiraCommentResponse createComment(String issueKey, CreateCommentRequest request) throws CustomException {
////        try {
////            log.debug("Creating comment for issue: {}", issueKey);
////
////            // Validate issue key format
////            if (issueKey == null || !issueKey.matches("[A-Za-z]{2,}-\\d+")) {
////                throw new CustomException("Invalid issue key format", HttpStatus.BAD_REQUEST);
////            }
////
////            // Validate comment request
////            if (request == null || request.getBody() == null) {
////                throw new CustomException("Comment body cannot be empty", HttpStatus.BAD_REQUEST);
////            }
////
////            String path = String.format("/issue/%s/comment", issueKey);
////            JiraCommentResponse response = jiraClientConfiguration.post(path, request, JiraCommentResponse.class);
////
////            if (response == null) {
////                log.error("Empty response from Jira API for issue: {}", issueKey);
////                throw new CustomException("Empty response from Jira API", HttpStatus.INTERNAL_SERVER_ERROR);
////            }
////
////            return response;
////
////        } catch (CustomException e) {
////            throw e;
////        } catch (Exception e) {
////            log.error("Failed to create comment for issue: {}", issueKey, e);
////            throw new CustomException("Failed to create comment for issue: " + issueKey,
////                    HttpStatus.INTERNAL_SERVER_ERROR);
////        }
////    }
//
//
//    @Override
//    public IssueResponse createIssueInJira(IssueRequest request) {
//        IssueResponse issueResponse = jiraClientConfiguration
//                .post("/issue", request, IssueResponse.class);
//
//        return jiraClientConfiguration
//                .get("/issue/" + issueResponse.getKey(), IssueResponse.class);
//
//    }
//
//
//}

