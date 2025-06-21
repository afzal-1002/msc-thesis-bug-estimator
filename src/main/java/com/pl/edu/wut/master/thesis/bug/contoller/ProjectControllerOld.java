//package com.pl.edu.wut.master.thesis.bug.controller;
//
//import com.pl.edu.wut.master.thesis.bug.exception.ResourceNotFoundException;
//import com.pl.edu.wut.master.thesis.bug.model.user.User;
//import com.pl.edu.wut.master.thesis.bug.service.ProjectService;
//import com.pl.edu.wut.master.thesis.bug.service.UserService;
//import com.pl.edu.wut.master.thesis.bug.mapper.ProjectMapper;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * Endpoints for managing projects tied to a given user.
// */
//@RestController
//@RequestMapping("/api/project/")
//@RequiredArgsConstructor
//public class ProjectController {
//
//    private final UserService userService;
//    private final ProjectService projectService;
//    private final ProjectMapper projectMapper;
//
//    /** List all projects for a specific user */
//    @GetMapping
//    public ResponseEntity<List<ProjectResponse>> listForUser(@PathVariable Long userId) {
//        User user = userService.getUserById(userId);
//        if (user == null) {
//            throw new ResourceNotFoundException("User not found with id " + userId);
//        }
//        List<ProjectResponse> resp = projectService.getProjectsForUser(user).stream()
//                .map(projectMapper::toResponse)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(resp);
//    }
//
//    /** Create a new project for a specific user */
//    @PostMapping("/{userId}/project")
//    public ResponseEntity<ProjectResponse> addProject(
//            @PathVariable Long userId,
//            @RequestBody ProjectRequest request) {
//
//        User user = userService.getUserById(userId);
//        if (user == null) {
//            throw new ResourceNotFoundException("User not found with id " + userId);
//        }
//        Project created = projectService.addProjectToUser(user, request);
//        return ResponseEntity.status(HttpStatus.CREATED).body(projectMapper.toResponse(created));
//    }
//
//    /** Update credentials or key for an existing project */
//    @PutMapping("/{userId}/{projectId}")
//    public ResponseEntity<ProjectResponse> updateProject(
//            @PathVariable Long userId,
//            @PathVariable Long projectId,
//            @RequestBody ProjectRequest request) {
//
//        // Optional: verify ownership, e.g. projectService.getProjectByKey(...)
//        Project updated = projectService.updateProjectCredentials(projectId, request);
//        return ResponseEntity.ok(projectMapper.toResponse(updated));
//    }
//
//    /** Delete a project */
//    @DeleteMapping("/{userId}/{projectId}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteProject(
//            @PathVariable Long userId,
//            @PathVariable Long projectId) {
//
//        // Optional: verify ownership
//        projectService.deleteProject(projectId);
//    }
//}
