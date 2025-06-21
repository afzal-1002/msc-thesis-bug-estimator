package com.pl.edu.wut.master.thesis.bug.service.implementation;

import com.pl.edu.wut.master.thesis.bug.dto.request.ImportProjectRequest;
import com.pl.edu.wut.master.thesis.bug.dto.response.ProjectResponse;
import com.pl.edu.wut.master.thesis.bug.mapper.ProjectCategoryMapper;
import com.pl.edu.wut.master.thesis.bug.mapper.ProjectMapper;
import com.pl.edu.wut.master.thesis.bug.mapper.UserSummaryMapper;
import com.pl.edu.wut.master.thesis.bug.model.jira.JiraCredentials;
import com.pl.edu.wut.master.thesis.bug.model.project.Project;
import com.pl.edu.wut.master.thesis.bug.model.user.User;
import com.pl.edu.wut.master.thesis.bug.repository.ProjectRepository;
import com.pl.edu.wut.master.thesis.bug.service.JiraClientService;
import com.pl.edu.wut.master.thesis.bug.service.ProjectService;
import com.pl.edu.wut.master.thesis.bug.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

@Slf4j
public class ProjectServiceImplementation implements ProjectService {


    private final ProjectRepository projectRepository;
    private final UserService userService;
    private final ProjectMapper projectMapper;
    private final JiraClientService jiraClientService;
    private final UserSummaryMapper userSummaryMapper;
    private final ProjectCategoryMapper projectCategoryMapper;

    @Override
    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(projectMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectResponse getProjectById(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + projectId));
        return projectMapper.toResponse(project);
    }

    @Override
    public Project findByKey(String projectKey) {
        return projectRepository.findByKey(projectKey)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with key: " + projectKey));
    }

    @Override
    public List<ProjectResponse> getProjectsForUser(Long userId) {
        User user = userService.findUserById(userId);

        return projectRepository.findByUsersContaining(user).stream()
                .map(projectMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProject(Long projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new EntityNotFoundException("Project not found with id: " + projectId);
        }
        projectRepository.deleteById(projectId);
    }

    @Override
    public ProjectResponse refreshProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + projectId));

        // Here you would typically add logic to refresh data from Jira
        return projectMapper.toResponse(project);
    }

    @Override
    public ProjectResponse updateProjectCredentials(Long projectId, ImportProjectRequest request) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + projectId));

        project.setJiraUsername(request.getJiraUsername());
        project.setUserToken(request.getJiraToken());
        project.setBaseUrl(request.getJiraBaseUrl());

        Project updatedProject = projectRepository.save(project);
        return projectMapper.toResponse(updatedProject);
    }

//    @Override
//    public ProjectResponse importProjectForUser(ImportProjectRequest request) {
//        User user = userService.findByUsername(request.getJiraUsername());
//
//        log.info("Importing project for user:  {} ", user.getUsername());
//
//        JiraCredentials credentials = new JiraCredentials(
//                request.getJiraBaseUrl(),
//                request.getJiraUsername(),
//                request.getJiraToken()
//        );
//
//        log.info("JiraCredentials for Project:  {} ", credentials.getJiraBaseUrl());
//
//        ProjectResponse jiraProject = jiraClientService.fetchProjectForUser(request);
//
//        log.info("jiraProject for Project:  {} ", jiraProject.getJiraId());
//
//        Project.ProjectBuilder builder = Project.builder();
//        builder.jiraId(jiraProject.getJiraId());
//        builder.key(jiraProject.getKey());
//        builder.name(jiraProject.getName());
//        builder.description(jiraProject.getDescription());
//        builder.lead(userSummaryMapper.toEntity(jiraProject.getLead()));
//        builder.category(projectCategoryMapper.toEntity(jiraProject.getCategory()));
//        builder.roles(jiraProject.getRoles());
//        builder.projectTypeKey(jiraProject.getProjectTypeKey());
//        builder.baseUrl(request.getJiraBaseUrl());
//        builder.jiraUsername(request.getJiraUsername());
//        builder.userToken(request.getJiraToken());
//        Project project = builder.build();
//
//        project = projectRepository.save(project);
//        user.getProjects().add(project);
//        userService.updateUser(user);
//
//        ProjectResponse.ProjectResponseBuilder buildered = ProjectResponse.builder();
//        buildered.jiraId(project.getJiraId());
//        buildered.key(project.getKey());
//        buildered.name(project.getName());
//        buildered.description(project.getDescription());
//        buildered.lead(userSummaryMapper.mapToResponse(project.getLead()));
//        buildered.category(projectCategoryMapper.mapToResponse(project.getCategory()));
//        buildered.roles(project.getRoles());
//        buildered.projectTypeKey(project.getProjectTypeKey());
//        buildered.baseUrl(project.getBaseUrl());
//        buildered.id(project.getId());
//        return buildered.build();
//    }




}