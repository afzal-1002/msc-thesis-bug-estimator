package com.pl.edu.wut.master.thesis.bug.contoller;

import com.pl.edu.wut.master.thesis.bug.dto.project.CreateProjectRequest;
import com.pl.edu.wut.master.thesis.bug.dto.project.ProjectResponse;
import com.pl.edu.wut.master.thesis.bug.dto.project.ProjectDetailsSummary;
import com.pl.edu.wut.master.thesis.bug.service.JiraProjectService;
import com.pl.edu.wut.master.thesis.bug.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/wut/projects")
@RequiredArgsConstructor
@Slf4j
public class ProjectController {

    private final ProjectService     projectService;
    private final JiraProjectService jiraProjectService;

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@RequestBody CreateProjectRequest request) {
        ProjectResponse created = projectService.createProject(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/sync")
    public ResponseEntity<List<ProjectResponse>> syncAll() {
        List<ProjectResponse> projectResponse = projectService.syncAllProject();
        return ResponseEntity.ok(projectResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getAllProjects() {
        List<ProjectResponse> projects = projectService.getAllProjectsFromDatabase();
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/user/{accountId}")
    public ResponseEntity<List<ProjectResponse>> getProjectsByAccountId(
            @PathVariable String accountId) {
        List<ProjectResponse> projects = projectService.getProjectsByUserAccountId(accountId);
        if (projects.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/lead/{accountId}")
    public ResponseEntity<List<ProjectResponse>> getProjectsByLead(
            @PathVariable String accountId) {
        List<ProjectResponse> list = projectService.getProjectsByLeadAccountId(accountId);
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{key}/details")
    public ResponseEntity<ProjectDetailsSummary> getProjectDetails(
            @PathVariable("key") String projectKey) {
        ProjectDetailsSummary response = jiraProjectService.getProjectDetailsByKeyOrId(projectKey);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/select")
    public ResponseEntity<Map<String,ProjectResponse>> selectProject(@PathVariable Long id) {
        ProjectResponse project = projectService.selectProject(id);
        return ResponseEntity.ok(Map.of("project: ", project));
    }

    @GetMapping("/view/{keyOrId}")
    public ResponseEntity<ProjectResponse> viewProjectByKeyOrId(@PathVariable String keyOrId) {
        return ResponseEntity.ok(projectService.viewProjectByKeyOrId(keyOrId));
    }

//    @GetMapping("/issues")
//    public ResponseEntity<String> getIssuesRaw() {
//        String jiraJson = projectIssueService.getProjectIssuesRaw();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        return new ResponseEntity<>(jiraJson, headers, HttpStatus.OK);
//    }


//    @PostMapping("/issues/sync/all")
//    public List<IssueResponse> syncAllIssues() {
//        List<Issue> issues = projectIssueService.syncAllIssues();
//        return issues.stream()
//                .map(issueMapper::toResponse)
//                .toList();
//    }


}
