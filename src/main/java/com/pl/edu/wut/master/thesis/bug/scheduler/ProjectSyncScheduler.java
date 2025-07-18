//package com.pl.edu.wut.master.thesis.jira.scheduler;
//
//import com.pl.edu.wut.master.thesis.jira.exception.CustomException;
//import com.pl.edu.wut.master.thesis.jira.exception.ExternalApiException;
//import com.pl.edu.wut.master.thesis.jira.model.project.Project;
//import com.pl.edu.wut.master.thesis.jira.service.ProjectService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.JiraComponent;
//
//import java.util.List;
//
//@JiraComponent
//@RequiredArgsConstructor
//@Slf4j
//public class ProjectSyncScheduler {
//
//    private final ProjectService projectService;
//
//    // Runs every 1 hour → you can change the cron expression
//    @Scheduled(cron = "0 0 * * * *")
//    public void syncProjectsFromJira() {
//        log.info("ProjectSyncScheduler: Starting sync of Projects from Jira...");
//
//        try {
//            // 1️⃣ Fetch Projects from Jira
//            String response = projectService.fetchProjectsFromJira();
//
//            // 2️⃣ Parse response → List<Project>
//            List<Project> projects = projectService.parseJiraProjectsResponse(response);
//
//            // 3️⃣ Save each project to DB
//            for (Project project : projects) {
//                projectService.saveProject(project);
//            }
//
//            log.info("ProjectSyncScheduler: Finished sync of Projects from Jira. Total Projects synced: {}", projects.size());
//
//        } catch (ExternalApiException ex) {
//            log.error("ProjectSyncScheduler: ExternalApiException while syncing projects from Jira: {}", ex.getMessage());
//
//        } catch (CustomException ex) {
//            log.error("ProjectSyncScheduler: CustomException while syncing projects from Jira: {}", ex.getMessage());
//
//        } catch (Exception ex) {
//            log.error("ProjectSyncScheduler: Unexpected error while syncing projects from Jira", ex);
//        }
//    }
//}