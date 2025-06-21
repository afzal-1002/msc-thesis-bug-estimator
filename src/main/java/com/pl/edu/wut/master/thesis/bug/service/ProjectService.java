
package com.pl.edu.wut.master.thesis.bug.service;

import com.pl.edu.wut.master.thesis.bug.dto.request.ImportProjectRequest;
import com.pl.edu.wut.master.thesis.bug.dto.response.ProjectResponse;
import com.pl.edu.wut.master.thesis.bug.model.project.Project;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProjectService {
    List<ProjectResponse> getAllProjects();
    ProjectResponse getProjectById(Long projectId);
    Project findByKey(String projectKey);
    List<ProjectResponse> getProjectsForUser(Long userId);
    void deleteProject(Long projectId);
    ProjectResponse refreshProject(Long projectId);
    ProjectResponse updateProjectCredentials(Long projectId, ImportProjectRequest request);


//    ProjectResponse importProjectForUser(ImportProjectRequest request);

//    ProjectResponse importProjectForUser(ImportProjectRequest request);
//    List<String> getProjectStatusesById(Long projectId);
//    List<String> getProjectComponentsById(Long projectId);
//    List<String> getProjectVersionsById(Long projectId);
}