package com.pl.edu.wut.master.thesis.bug.service;

import com.pl.edu.wut.master.thesis.bug.dto.request.*;
import com.pl.edu.wut.master.thesis.bug.dto.response.*;
import org.springframework.stereotype.Service;

@Service
public interface JiraClientService {


    public ProjectResponse fetchProject(ImportProjectRequest request);
    public ProjectResponse fetchProjectForUser(ImportProjectRequest credentials);


//    List<String> fetchProjectStatuses(String projectKey, ProjectRequest credentials);
//    List<String> fetchProjectComponents(String projectKey, ProjectRequest credentials);
//    List<String> fetchProjectVersions(String projectKey, ProjectRequest credentials);
//    void archiveProject(String projectKey, ProjectRequest credentials);
//    void restoreProject(String projectKey, ProjectRequest credentials);


}