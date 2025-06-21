//package com.pl.edu.wut.master.thesis.bug.controller;
//
//import com.pl.edu.wut.master.thesis.bug.dto.request.*;
//import com.pl.edu.wut.master.thesis.bug.dto.response.*;
//import com.pl.edu.wut.master.thesis.bug.service.JiraClientService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/jira")
//@RequiredArgsConstructor
//public class JiraApiController {
//
//    private final JiraClientService jiraClientService;
//
//    // --- Bugs/Issues ---
//
//    @GetMapping("/bugs")
//    public ResponseEntity<SearchResultResponse<BugSummaryResponse>> findAllBugs() {
//        return ResponseEntity.ok(jiraClientService.findAllBugs());
//    }
//
//    @GetMapping("/issues/{key}")
//    public ResponseEntity<IssueResponse> getIssue(@PathVariable String key) {
//        return ResponseEntity.ok(jiraClientService.getIssueByKey(key));
//    }
//
//    @PostMapping("/issues")
//    public ResponseEntity<IssueResponse> createIssue(@RequestBody IssueRequest payload) {
//        return ResponseEntity.ok(jiraClientService.createIssue(payload));
//    }
//
//    @PutMapping("/issues/{key}")
//    public ResponseEntity<IssueResponse> updateIssue(
//            @PathVariable String key,
//            @RequestBody UpdateIssueRequest payload) {
//        return ResponseEntity.ok(jiraClientService.updateIssue(key, payload));
//    }
//
//    @DeleteMapping("/issues/{key}")
//    public ResponseEntity<Void> deleteIssue(@PathVariable String key) {
//        jiraClientService.deleteIssue(key);
//        return ResponseEntity.noContent().build();
//    }
//
//    @PostMapping("/issues/{key}/transitions")
//    public ResponseEntity<Void> transitionIssue(
//            @PathVariable String key,
//            @RequestBody TransitionIssueRequest payload) {
//        jiraClientService.transitionIssue(key, payload);
//        return ResponseEntity.noContent().build();
//    }
//
//    // --- Users ---
//
//    @GetMapping("/users/search")
//    public ResponseEntity<List<UserSummaryResponse>> searchUsers(
//            @RequestParam String query) {
//        return ResponseEntity.ok(jiraClientService.searchUsers(query));
//    }
//
//    @GetMapping("/users/me")
//    public ResponseEntity<UserSummaryResponse> getCurrentUser() {
//        return ResponseEntity.ok(jiraClientService.getCurrentUser());
//    }
//
//    // --- Metadata ---
//
//    @GetMapping("/fields")
//    public ResponseEntity<List<FieldResponse>> getFields() {
//        return ResponseEntity.ok(jiraClientService.getFields());
//    }
//
//    @GetMapping("/issuetypes")
//    public ResponseEntity<List<IssueTypeResponse>> getIssueTypes() {
//        return ResponseEntity.ok(jiraClientService.getIssueTypes());
//    }
//
//    @GetMapping("/priorities")
//    public ResponseEntity<List<PriorityResponse>> getPriorities() {
//        return ResponseEntity.ok(jiraClientService.getPriorities());
//    }
//
//    @GetMapping("/createmeta")
//    public ResponseEntity<CreateMetaResponse> getCreateMeta() {
//        return ResponseEntity.ok(jiraClientService.getCreateMeta());
//    }
//
//    // --- Projects ---
//
//    @GetMapping("/projects")
//    public ResponseEntity<List<ProjectSummaryResponse>> getAllProjects() {
//        return ResponseEntity.ok(jiraClientService.getAllProjects());
//    }
//
//    @GetMapping("/projects/{key}")
//    public ResponseEntity<ProjectResponse> getProjectByKey(
//            @PathVariable String key) {
//        return ResponseEntity.ok(jiraClientService.getProjectByKey(key));
//    }
//
//    @PostMapping("/projects")
//    public ResponseEntity<ProjectResponse> createProject(
//            @RequestBody CreateProjectRequest payload) {
//        return ResponseEntity.ok(jiraClientService.createProject(payload));
//    }
//
//    @PutMapping("/projects/{key}")
//    public ResponseEntity<ProjectResponse> updateProject(
//            @PathVariable String key,
//            @RequestBody UpdateProjectRequest payload) {
//        return ResponseEntity.ok(jiraClientService.updateProject(key, payload));
//    }
//
//    @DeleteMapping("/projects/{key}")
//    public ResponseEntity<Void> deleteProject(@PathVariable String key) {
//        jiraClientService.deleteProject(key);
//        return ResponseEntity.noContent().build();
//    }
//
//    @PatchMapping("/projects/{key}")
//    public ResponseEntity<ProjectResponse> patchProject(@PathVariable String key, @RequestBody PatchProjectRequest payload) {
//        return ResponseEntity.ok(jiraClientService.patchProject(key, payload));
//    }
//
//    /** Fetch & persist a project by its local DB ID */
//    @PostMapping("/projects/fetch")
//    public ResponseEntity<ProjectResponse> fetchProjectById(@RequestBody ProjectRequest request) {
//        // assume ProjectRequest has a getProjectKey() method
//        ProjectResponse dto = jiraClientService.getProjectById();
//        return ResponseEntity.ok(dto);
//    }
//
//    // --- Comments ---
//
//    @GetMapping("/issues/{key}/comments")
//    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable String key) {
//        return ResponseEntity.ok(jiraClientService.getComments(key));
//    }
//
//    @PostMapping("/issues/{key}/comments")
//    public ResponseEntity<CommentResponse> addComment(@PathVariable String key,
//            @RequestBody CreateCommentRequest payload) {
//        return ResponseEntity.ok(jiraClientService.addComment(key, payload));
//    }
//
//    @PutMapping("/issues/{key}/comments/{commentId}")
//    public ResponseEntity<CommentResponse> updateComment(
//            @PathVariable String key,
//            @PathVariable String commentId,
//            @RequestBody UpdateCommentRequest payload) {
//        return ResponseEntity.ok(jiraClientService.updateComment(key, commentId, payload));
//    }
//
//    @DeleteMapping("/issues/{key}/comments/{commentId}")
//    public ResponseEntity<Void> deleteComment(
//            @PathVariable String key,
//            @PathVariable String commentId) {
//        jiraClientService.deleteComment(key, commentId);
//        return ResponseEntity.noContent().build();
//    }
//
//    // --- Attachments ---
//
//    @PostMapping("/issues/{key}/attachments")
//    public ResponseEntity<AttachmentResponse> addAttachment(
//            @PathVariable String key,
//            @RequestParam("fileName") String fileName,
//            @RequestBody byte[] fileContent) {
//        return ResponseEntity.ok(jiraClientService.addAttachment(key, fileContent, fileName));
//    }
//
//    // --- Worklogs ---
//
//    @GetMapping("/issues/{key}/worklogs")
//    public ResponseEntity<List<WorklogResponse>> getWorklogs(
//            @PathVariable String key) {
//        return ResponseEntity.ok(jiraClientService.getWorklogs(key));
//    }
//
//    @PostMapping("/issues/{key}/worklogs")
//    public ResponseEntity<WorklogResponse> addWorklog(
//            @PathVariable String key,
//            @RequestBody CreateWorklogRequest payload) {
//        return ResponseEntity.ok(jiraClientService.addWorklog(key, payload));
//    }
//
//    @DeleteMapping("/issues/{key}/worklogs/{worklogId}")
//    public ResponseEntity<Void> deleteWorklog(
//            @PathVariable String key,
//            @PathVariable String worklogId) {
//        jiraClientService.deleteWorklog(key, worklogId);
//        return ResponseEntity.noContent().build();
//    }
//
//    // --- Transitions & Watchers ---
//
//    @GetMapping("/issues/{key}/transitions")
//    public ResponseEntity<List<TransitionResponse>> getTransitions(
//            @PathVariable String key) {
//        return ResponseEntity.ok(jiraClientService.getTransitions(key));
//    }
//
//    @GetMapping("/issues/{key}/watchers")
//    public ResponseEntity<WatchersResponse> getWatchers(
//            @PathVariable String key) {
//        return ResponseEntity.ok(jiraClientService.getWatchers(key));
//    }
//
//    @PostMapping("/issues/{key}/watchers")
//    public ResponseEntity<Void> addWatcher(
//            @PathVariable String key,
//            @RequestBody String userAccountId) {
//        jiraClientService.addWatcher(key, userAccountId);
//        return ResponseEntity.noContent().build();
//    }
//
//    @DeleteMapping("/issues/{key}/watchers")
//    public ResponseEntity<Void> removeWatcher(
//            @PathVariable String key,
//            @RequestParam String accountId) {
//        jiraClientService.removeWatcher(key, accountId);
//        return ResponseEntity.noContent().build();
//    }
//
//    // --- Roles / Versions / Components / Changelog ---
//
//    @GetMapping("/projects/{key}/roles")
//    public ResponseEntity<List<RoleResponse>> getProjectRoles(
//            @PathVariable String key) {
//        return ResponseEntity.ok(jiraClientService.getProjectRoles(key));
//    }
//
//    @GetMapping("/roles")
//    public ResponseEntity<List<RoleTypeResponse>> getRoleTypes() {
//        return ResponseEntity.ok(jiraClientService.getRoleTypes());
//    }
//
//    @GetMapping("/projects/{key}/versions")
//    public ResponseEntity<List<VersionResponse>> getProjectVersions(
//            @PathVariable String key) {
//        return ResponseEntity.ok(jiraClientService.getProjectVersions(key));
//    }
//
//    @GetMapping("/projects/{key}/components")
//    public ResponseEntity<List<ComponentResponse>> getProjectComponents(
//            @PathVariable String key) {
//        return ResponseEntity.ok(jiraClientService.getProjectComponents(key));
//    }
//
//    @GetMapping("/issues/{key}/changelog")
//    public ResponseEntity<ChangelogResponse> getIssueChangelog(
//            @PathVariable String key) {
//        return ResponseEntity.ok(jiraClientService.getIssueChangelog(key));
//    }
//}
