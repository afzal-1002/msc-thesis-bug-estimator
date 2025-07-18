//package com.pl.edu.wut.master.thesis.jira.scheduler;
//
//import com.pl.edu.wut.master.thesis.jira.dto.request.IssueReq;
//import com.pl.edu.wut.master.thesis.jira.exception.ExternalApiException;
//import com.pl.edu.wut.master.thesis.jira.mapper.BugMapper;
//import com.pl.edu.wut.master.thesis.jira.model.jira.Issue;
//import com.pl.edu.wut.master.thesis.jira.service.BugService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.JiraComponent;
//
//import java.util.List;
//
//@Slf4j
//@JiraComponent
//@RequiredArgsConstructor
//public class BugSyncScheduler {
//
//    private final BugService bugService;
//    private final BugMapper bugMapper;
//
//    @Scheduled(cron = "0 */30 * * * *")
//    public void syncBugsFromJira() {
//        log.info("Starting sync of Bugs from Jira…");
//
//        try {
//            String raw = bugService.fetchBugsFromJira();
//            List<Issue> bugs = bugService.parseJiraBugsResponse(raw);
//
//            for (Issue jira : bugs) {
//                // just one map call: entity → request‐DTO → upsert
//                bugService.saveOrUpdate(bugMapper.mapToBugRequest(jira));
//            }
//
//            log.info("Finished sync. Total bugs: {}", bugs.size());
//        } catch (Exception ex) {
//            log.error("Error syncing bugs from Jira", ex);
//        }
//    }
//}