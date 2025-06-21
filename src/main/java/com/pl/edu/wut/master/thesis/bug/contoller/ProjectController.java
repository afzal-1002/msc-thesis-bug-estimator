package com.pl.edu.wut.master.thesis.bug.contoller;

import com.pl.edu.wut.master.thesis.bug.dto.request.ImportProjectRequest;
import com.pl.edu.wut.master.thesis.bug.dto.response.ProjectResponse;
import com.pl.edu.wut.master.thesis.bug.model.project.Project;
import com.pl.edu.wut.master.thesis.bug.service.ProjectService;
import com.pl.edu.wut.master.thesis.bug.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable Long projectId) {
        return ResponseEntity.ok(projectService.getProjectById(projectId));
    }

    @GetMapping("/key/{projectKey}")
    public ResponseEntity<Project> getProjectByKey(@PathVariable String projectKey) {
        return ResponseEntity.ok(projectService.findByKey(projectKey));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProjectResponse>> getProjectsForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(projectService.getProjectsForUser(userId));
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{projectId}/refresh")
    public ResponseEntity<ProjectResponse> refreshProject(@PathVariable Long projectId) {
        return ResponseEntity.ok(projectService.refreshProject(projectId));
    }

    @PutMapping("/{projectId}/credentials")
    public ResponseEntity<ProjectResponse> updateCredentials(
            @PathVariable Long projectId,
            @RequestBody ImportProjectRequest request) {
        return ResponseEntity.ok(projectService.updateProjectCredentials(projectId, request));
    }

//    @PostMapping("/import")
//    public ProjectResponse importProject(@RequestBody ImportProjectRequest request) {
//        return projectService.importProjectForUser(request);
//    }
}