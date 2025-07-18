// src/main/java/com/pl/edu/wut/master/thesis/bug/service/ProjectService.java
package com.pl.edu.wut.master.thesis.bug.service;

import com.pl.edu.wut.master.thesis.bug.dto.project.CreateProjectRequest;
import com.pl.edu.wut.master.thesis.bug.dto.project.ProjectResponse;
import com.pl.edu.wut.master.thesis.bug.dto.project.ProjectSummary;
import com.pl.edu.wut.master.thesis.bug.enums.ProjectTypeKey;
import com.pl.edu.wut.master.thesis.bug.model.project.Project;
import com.pl.edu.wut.master.thesis.bug.model.project.ProjectReference;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    ProjectResponse createProject(CreateProjectRequest request);
    List<ProjectSummary> fetchAllJiraProjects();

    Optional<Project> find(ProjectReference reference);
    // ─── CRUD & lookup on your Project entity ───────────────
    Optional<Project>           findById(Long id);
    Optional<Project>           findByKey(String key);
    List<Project>              findAllWithUsersAndIssues();
    Project                    saveWithUsers(Project project);

    // ─── DTO‐mapping & response construction ────────────────
    ProjectResponse            buildProjectResponse(Project project);
    List<ProjectResponse>      getAllProjectsFromDatabase();
    ProjectResponse            getProjectById(Long id);
    ProjectResponse            viewProjectByKeyOrId(String keyOrId);

    // ─── User‐scoped lookups ────────────────────────────────
    List<ProjectResponse>      getProjectsByUserAccountId(String accountId);
    List<ProjectResponse>      getProjectsByLeadAccountId(String accountId);

    // ─── Sync (uses JiraClientService under the hood)────────
    List<ProjectResponse>      syncAllProject();
    List<ProjectResponse>      syncAllProject(List<ProjectTypeKey> allowedTypes);

    // ─── “select” flow (session/cache)───────────────────────
    ProjectResponse                     selectProject(Long projectId);




}
