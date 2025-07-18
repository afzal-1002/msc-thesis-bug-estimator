package com.pl.edu.wut.master.thesis.bug.service.implementation;

import com.pl.edu.wut.master.thesis.bug.configuration.JiraClientConfiguration;
import com.pl.edu.wut.master.thesis.bug.dto.project.CreateProjectRequest;
import com.pl.edu.wut.master.thesis.bug.dto.project.CreateProjectResponse;
import com.pl.edu.wut.master.thesis.bug.dto.project.ProjectResponse;
import com.pl.edu.wut.master.thesis.bug.dto.project.ProjectDetailsSummary;
import com.pl.edu.wut.master.thesis.bug.dto.project.ProjectSummary;
import com.pl.edu.wut.master.thesis.bug.enums.ProjectTypeKey;
import com.pl.edu.wut.master.thesis.bug.exception.ResourceNotFoundException;
import com.pl.edu.wut.master.thesis.bug.exception.UserNotAuthorizedException;
import com.pl.edu.wut.master.thesis.bug.mapper.ProjectMapper;
import com.pl.edu.wut.master.thesis.bug.model.project.Project;
import com.pl.edu.wut.master.thesis.bug.model.project.ProjectReference;
import com.pl.edu.wut.master.thesis.bug.model.user.User;
import com.pl.edu.wut.master.thesis.bug.model.user.UserSummary;
import com.pl.edu.wut.master.thesis.bug.repository.ProjectRepository;
import com.pl.edu.wut.master.thesis.bug.service.JiraProjectService;
import com.pl.edu.wut.master.thesis.bug.service.ProjectService;
import com.pl.edu.wut.master.thesis.bug.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectServiceImplementation implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final JiraProjectService jiraProjectService;
    private final JiraClientConfiguration jiraClientConfiguration;
    private final UserService userService;
    private final HttpSession session;

    private Long requireUserId() {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            throw new UserNotAuthorizedException("No logged-in user in session");
        }
        return userId;
    }

    private String requireBaseUrl() {
        return jiraClientConfiguration.getSessionBaseUrl();
    }

    @Override
    @Transactional
    public ProjectResponse createProject(CreateProjectRequest request) {
        Long userId = requireUserId();
        User current = userService.findUserById(userId);

        CreateProjectResponse jiraResp =
                jiraProjectService.createProject(request, current);

        Project project = projectMapper.toProjectEntity(request);
        project.setId(Long.valueOf(jiraResp.getId()));
        project.setKey(jiraResp.getKey());
        project.setBaseUrl(requireBaseUrl());

        UserSummary lead = UserSummary.builder()
                .accountId(current.getAccountId())
                .emailAddress(current.getEmailAddress())
                .displayName(current.getDisplayName())
                .username(current.getJiraCredential().getUsername())
                .active(current.isActive())
                .build();
        project.setLead(lead);
        project.getUsers().add(current);
        current.getProjects().add(project);

        Project saved = projectRepository.save(project);
        return projectMapper.fromProjectToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectResponse selectProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found: " + projectId));

        ProjectResponse projectResponse = projectMapper.fromProjectToResponse(project);
        session.setAttribute("projectKey", project.getKey());
        session.setAttribute("projectId", project.getId());

        return projectResponse;
    }

    @Override
    public ProjectResponse viewProjectByKeyOrId(String keyOrId) {
        Project project = resolveProjectEntity(keyOrId);

        session.setAttribute("projectKey", project.getKey());
        session.setAttribute("projectId", project.getId());
        return projectMapper.fromProjectToResponse(project);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponse> getAllProjectsFromDatabase() {
        return projectRepository.findAllWithUsersAndIssues().stream()
                .map(projectMapper::fromProjectToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectResponse getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found: " + id));
        return projectMapper.fromProjectToResponse(project);
    }

    @Override
    @Transactional
    public List<ProjectResponse> syncAllProject() {
        Long currentUserId = requireUserId();
        User currentUser = userService.findUserByIdWithProjects(currentUserId);
        String activeBaseUrl = requireBaseUrl();
        log.info("Starting sync for user {} at base URL: {}", currentUser.getAccountId(), activeBaseUrl);

        Map<String, Project> localProjectByKey = projectRepository
                .findAllWithUsersAndIssues().stream()
                .filter(project -> activeBaseUrl.equals(project.getBaseUrl()))
                .collect(Collectors.toMap(Project::getKey, project -> project));
        log.info("Found {} local projects for this base URL.", localProjectByKey.size());

        Set<Long> linkedProjectIds = new HashSet<>(currentUser.getProjects().stream()
                .map(Project::getId)
                .collect(Collectors.toSet()));
        log.info("User is currently linked to {} projects.", linkedProjectIds.size());

        List<ProjectResponse> responseList = new ArrayList<>();
        List<ProjectSummary> jiraProjectList;
        try {
            jiraProjectList = jiraProjectService.getAllProjectsFromJira();
            log.info("Found {} projects from Jira API.", jiraProjectList.size());
        } catch (Exception e) {
            log.error("Failed to fetch projects from Jira API: {}", e.getMessage());
            return new ArrayList<>();
        }

        if (jiraProjectList.isEmpty()) {
            log.warn("Jira project list is empty. No projects to sync.");
            return responseList;
        }

        for (ProjectSummary jiraDto : jiraProjectList) {
            String projectKey = jiraDto.getKey();
            Project projectEntity = localProjectByKey.get(projectKey);

            if (projectEntity == null) {
                log.info("New project found in Jira. Creating local entity for key: {}", projectKey);
                Project newProject = Project.builder()
                        .id(Long.valueOf(jiraDto.getId()))
                        .key(jiraDto.getKey())
                        .name(jiraDto.getName())
                        .baseUrl(activeBaseUrl)
                        .projectTypeKey(jiraDto.getProjectTypeKey())
                        .description("")
                        .users(new HashSet<>())
                        .issues(new HashSet<>())
                        .build();
                projectEntity = projectRepository.save(newProject);
                log.info("New project with key {} saved with ID: {}", projectKey, projectEntity.getId());
            } else {
                log.info("Existing project found locally for key: {}", projectKey);
                boolean projectChanged = false;
                if (!Objects.equals(projectEntity.getName(), jiraDto.getName())) {
                    projectEntity.setName(jiraDto.getName());
                    projectChanged = true;
                }
                if (!Objects.equals(projectEntity.getProjectTypeKey(), jiraDto.getProjectTypeKey())) {
                    projectEntity.setProjectTypeKey(jiraDto.getProjectTypeKey());
                    projectChanged = true;
                }
                if (projectChanged) {
                    projectEntity = projectRepository.save(projectEntity);
                    log.info("Project with key {} was updated.", projectKey);
                }
            }

            try {
                ProjectDetailsSummary details = jiraProjectService.getProjectDetailsByKeyOrId(projectKey);
                String jiraDescription = details.getDescription();
                if (jiraDescription != null && !jiraDescription.equals(projectEntity.getDescription())) {
                    projectEntity.setDescription(jiraDescription);
                    projectEntity = projectRepository.save(projectEntity);
                    log.info("Description for project {} was updated.", projectKey);
                }
            } catch (Exception e) {
                log.warn("Could not fetch details for project {}. Skipping description update. Reason: {}", projectKey, e.getMessage());
            }

            if (!linkedProjectIds.contains(projectEntity.getId())) {
                log.info("Linking user to new project: {}", projectKey);
                currentUser.addProject(projectEntity);
                linkedProjectIds.add(projectEntity.getId());
            }

            responseList.add(projectMapper.fromProjectToResponse(projectEntity));
        }

        if (currentUser.getProjects().size() > linkedProjectIds.size()) {
            log.info("User's project links were updated. Saving user entity.");
            userService.saveAndFlushUser(currentUser);
        } else {
            log.info("No new projects were linked to the user.");
        }

        log.info("Sync completed successfully. Total projects linked: {}", linkedProjectIds.size());
        return responseList;
    }

    @Override
    @Transactional
    public List<ProjectResponse> syncAllProject(List<ProjectTypeKey> allowedTypes) {
        Long currentUserId = requireUserId();
        User currentUser = userService.findUserByIdWithProjects(currentUserId);
        String activeBaseUrl = requireBaseUrl();
        log.info("Starting filtered sync for user {} with allowed types {} at base URL: {}", currentUser.getAccountId(), allowedTypes, activeBaseUrl);

        Map<String, Project> localByKey = projectRepository
                .findAllWithUsersAndIssues().stream()
                .filter(p -> activeBaseUrl.equals(p.getBaseUrl()))
                .collect(Collectors.toMap(Project::getKey, p -> p));
        log.info("Found {} local projects for this base URL.", localByKey.size());

        Set<Long> linkedProjectIds = new HashSet<>(currentUser.getProjects().stream()
                .map(Project::getId).collect(Collectors.toSet()));
        log.info("User is currently linked to {} projects.", linkedProjectIds.size());

        List<ProjectResponse> responseList = new ArrayList<>();
        List<ProjectSummary> jiraAll;
        try {
            jiraAll = jiraProjectService.getAllProjectsFromJira();
            log.info("Found {} projects from Jira API.", jiraAll.size());
        } catch (Exception e) {
            log.error("Failed to fetch projects from Jira API: {}", e.getMessage());
            return new ArrayList<>();
        }

        if (jiraAll.isEmpty()) {
            log.warn("Jira project list is empty. No projects to sync.");
            return responseList;
        }

        for (ProjectSummary jiraDto : jiraAll) {
            if (allowedTypes != null && !allowedTypes.isEmpty()) {
                ProjectTypeKey type = ProjectTypeKey.fromString(jiraDto.getProjectTypeKey());
                if (!allowedTypes.contains(type)) {
                    log.debug("Skipping project {} with type {} as it is not in the allowed list.", jiraDto.getKey(), jiraDto.getProjectTypeKey());
                    continue;
                }
            }

            String projectKey = jiraDto.getKey();
            Project projectEntity = localByKey.get(projectKey);

            if (projectEntity == null) {
                log.info("New project found in Jira. Creating local entity for key: {}", projectKey);
                Project newProject = Project.builder()
                        .id(Long.valueOf(jiraDto.getId()))
                        .key(jiraDto.getKey())
                        .name(jiraDto.getName())
                        .baseUrl(activeBaseUrl)
                        .projectTypeKey(jiraDto.getProjectTypeKey())
                        .description("")
                        .users(new HashSet<>())
                        .issues(new HashSet<>())
                        .build();
                projectEntity = projectRepository.save(newProject);
                log.info("New project with key {} saved with ID: {}", projectKey, projectEntity.getId());
            } else {
                log.info("Existing project found locally for key: {}", projectKey);
                boolean changed = false;
                if (!Objects.equals(projectEntity.getName(), jiraDto.getName())) {
                    projectEntity.setName(jiraDto.getName());
                    changed = true;
                }
                if (!Objects.equals(projectEntity.getProjectTypeKey(), jiraDto.getProjectTypeKey())) {
                    projectEntity.setProjectTypeKey(jiraDto.getProjectTypeKey());
                    changed = true;
                }
                if (changed) {
                    projectEntity = projectRepository.save(projectEntity);
                    log.info("Project with key {} was updated.", projectKey);
                }
            }

            try {
                ProjectDetailsSummary details = jiraProjectService.getProjectDetailsByKeyOrId(projectKey);
                String desc = details.getDescription();
                if (desc != null && !desc.equals(projectEntity.getDescription())) {
                    projectEntity.setDescription(desc);
                    projectEntity = projectRepository.save(projectEntity);
                    log.info("Description for project {} was updated.", projectKey);
                }
            } catch (Exception e) {
                log.warn("Could not fetch details for project {}. Skipping description update. Reason: {}", projectKey, e.getMessage());
            }

            if (!linkedProjectIds.contains(projectEntity.getId())) {
                log.info("Linking user to new project: {}", projectKey);
                currentUser.addProject(projectEntity);
                linkedProjectIds.add(projectEntity.getId());
            }

            responseList.add(projectMapper.fromProjectToResponse(projectEntity));
        }

        if (currentUser.getProjects().size() > linkedProjectIds.size()) {
            log.info("User's project links were updated. Saving user entity.");
            userService.saveAndFlushUser(currentUser);
        } else {
            log.info("No new projects were linked to the user.");
        }

        log.info("Filtered sync completed successfully. Total projects linked: {}", linkedProjectIds.size());
        return responseList;
    }

    @Override
    public List<ProjectSummary> fetchAllJiraProjects() {
        return jiraProjectService.getAllProjectsFromJira();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponse> getProjectsByUserAccountId(String accountId) {
        return projectRepository.findAllByUsers_AccountId(accountId).stream()
                .map(projectMapper::fromProjectToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponse> getProjectsByLeadAccountId(String accountId) {
        return projectRepository.findAllByLead_AccountId(accountId).stream()
                .map(projectMapper::fromProjectToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Project> findById(Long id) {
        return projectRepository.findById(id);
    }

    @Override
    public Optional<Project> find(ProjectReference reference) {
        if (reference.getId() != null) {
            return projectRepository.findById(Long.valueOf(reference.getId()));
        } else if (reference.getKey() != null) {
            return projectRepository.findByKey(reference.getKey());
        } else {
            throw new IllegalArgumentException(
                    "ProjectRef must contain either id or key");
        }
    }

    @Override
    public Optional<Project> findByKey(String key) {
        return projectRepository.findByKey(key);
    }

    @Override
    public List<Project> findAllWithUsersAndIssues() {
        return projectRepository.findAllWithUsersAndIssues();
    }

    @Override
    public Project saveWithUsers(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public ProjectResponse buildProjectResponse(Project project) {
        return projectMapper.fromProjectToResponse(project);
    }

    private Project resolveProjectEntity(String keyOrId) {
        try {
            Long id = Long.valueOf(keyOrId);
            return projectRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Project not found: " + keyOrId));
        } catch (NumberFormatException e) {
            return projectRepository.findByKey(keyOrId)
                    .orElseThrow(() -> new ResourceNotFoundException("Project not found: " + keyOrId));
        }
    }
}