//package com.pl.edu.wut.master.thesis.bug.service;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.pl.edu.wut.master.thesis.bug.configuration.JiraClientConfiguration;
//import com.pl.edu.wut.master.thesis.bug.dto.issue.response.IssueResponse;
//import com.pl.edu.wut.master.thesis.bug.dto.user.usersession.SessionContext;
//import com.pl.edu.wut.master.thesis.bug.enums.PriorityEnum;
//import com.pl.edu.wut.master.thesis.bug.enums.SynchronizationStatus;
//import com.pl.edu.wut.master.thesis.bug.mapper.IssueMapper;
//import com.pl.edu.wut.master.thesis.bug.model.common.TimeTracking;
//import com.pl.edu.wut.master.thesis.bug.model.issue.Issue;
//import com.pl.edu.wut.master.thesis.bug.dto.project.project_issue.IssueSearchResult;
//import com.pl.edu.wut.master.thesis.bug.model.issuetype.IssueType;
//import com.pl.edu.wut.master.thesis.bug.model.jira.IssueRecord;
//import com.pl.edu.wut.master.thesis.bug.model.issue.jira.create.IssueResponse;
//import com.pl.edu.wut.master.thesis.bug.model.user.UserSummary;
//import com.pl.edu.wut.master.thesis.bug.repository.IssueRepository;
//import com.pl.edu.wut.master.thesis.bug.repository.IssueTypeRepository;
//import com.pl.edu.wut.master.thesis.bug.repository.ProjectRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.Instant;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.ZoneOffset;
//import java.time.OffsetDateTime;
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//@Log4j2
//public class ProjectIssueService {
//
//    private final JiraClientConfiguration jiraClientConfiguration;
//    private final JiraIssueService jiraIssueService;
//    private final IssueRepository issueRepository;
//    private final IssueTypeRepository issueTypeRepository;
//    private final ProjectRepository projectRepository;
//    private final IssueMapper issueMapper;
//
//    /**
//     * Synchronizes all JIRA issues into the local database,
//     * deletes any that were removed in JIRA, and returns
//     * a list of IssueResponse DTOs.
//     */
//    public List<IssueResponse> syncAllIssues() {
//        // 0) Fetch from Jira + session context
//        IssueSearchResult projectIssuesResponse = jiraIssueService.fetchAllIssues();
//        SessionContext context = jiraClientConfiguration.loadSessionContext();
//        Long projectId = context.getProjectId();
//        String projectKey = context.getProjectKey();
//
//        // 1) Gather all JIRA keys
//        Set<String> seenKeys = projectIssuesResponse.getIssues().stream()
//                .map(IssueRecord::getKey)
//                .collect(Collectors.toSet());
//
//        // 2) Upsert each into local DB
//        List<Issue> persisted = new ArrayList<>(seenKeys.size());
//        for (IssueRecord jiraIssue : projectIssuesResponse.getIssues()) {
//            // a) Upsert IssueType
//            var jt = jiraIssue.getFields().getIssuetype();
//            IssueType type = issueTypeRepository.findById(jt.getId())
//                    .orElseGet(() -> issueTypeRepository.save(
//                            IssueType.builder()
//                                    .id(jt.getId())
//                                    .name(jt.getName())
//                                    .description(jt.getDescription())
//                                    .iconUrl(jt.getIconUrl())
//                                    .self(jt.getSelf())
//                                    .subtask(jt.isSubtask())
//                                    .hierarchyLevel(jt.getHierarchyLevel())
//                                    .avatarId(jt.getAvatarId())
//                                    .projectKey(projectKey)
//                                    .build()
//                    ));
//
//            // b) Fetch or new Issue entity
//            String key = jiraIssue.getKey();
//            Issue issue = issueRepository.findByKey(key).orElseGet(Issue::new);
//
//            // c) Build TimeTracking
//            TimeTracking tt = new TimeTracking();
//            int nowSec = OffsetDateTime.now().getSecond();
//            tt.setOriginalEstimateSeconds(nowSec);
//            tt.setTimeSpentSeconds(nowSec);
//            tt.setRemainingEstimateSeconds(nowSec);
//            tt.setAiEstimationSeconds(nowSec);
//
//            // d) Map users
//            UserSummary assignee = mapUser(jiraIssue.getFields().getAssignee());
//            UserSummary reporter = mapUser(jiraIssue.getFields().getReporter());
//
//            // e) Populate or overwrite
//            issue.setId(Long.valueOf(jiraIssue.getId()));
//            issue.setKey(key);
//            issue.setSummary(jiraIssue.getFields().getSummary());
//            issue.setDescription(extractDescription(jiraIssue.getFields().getDescription()));
//            issue.setStatus(jiraIssue.getFields().getStatus().getName());
//            issue.setIssueType(type);
//            issue.setPriorityEnum(PriorityEnum.valueOf(
//                    jiraIssue.getFields().getPriorityEnum().getName().toUpperCase()));
//            issue.setEnvironment(jiraIssue.getFields().getEnvironment());
//            issue.setParentKey(
//                    Optional.ofNullable(jiraIssue.getFields().getParent())
//                            .map(p -> p.getKey())
//                            .orElse(null)
//            );
//            issue.setLabels(new HashSet<>(
//                    Optional.ofNullable(jiraIssue.getFields().getLabels())
//                            .orElse(List.of())
//            ));
//            issue.setTimeTracking(tt);
//            issue.setProject(projectRepository.findById(projectId)
//                    .orElseThrow(() -> new IllegalStateException("Project not found")));
//            issue.setAssignee(assignee);
//            issue.setReporter(reporter);
//            issue.setCreatedAt(toLocalDateTime(jiraIssue.getFields().getCreated()));
//            issue.setUpdatedAt(toLocalDateTime(jiraIssue.getFields().getUpdated()));
//            issue.setResolvedAt(toLocalDateTime(jiraIssue.getFields().getResolutiondate()));
//            issue.setDueDate(toLocalDate(jiraIssue.getFields().getDuedate()));
//            issue.setSelfUrl(jiraIssue.getSelf());
//            issue.setSyncStatus(SynchronizationStatus.SYNCED);
//
//            persisted.add(issueRepository.save(issue));
//        }
//
//        // 3) Delete any locals not in JIRA
//        List<Issue> allLocal = issueRepository.findByProjectId(projectId);
//        List<Issue> toDelete = allLocal.stream()
//                .filter(i -> !seenKeys.contains(i.getKey()))
//                .toList();
//        if (!toDelete.isEmpty()) {
//            issueRepository.deleteAll(toDelete);
//        }
//
//        // 4) Map to DTOs
//        return persisted.stream()
//                .map(issueMapper::toResponse)
//                .toList();
//    }
//
//    // ─── Helpers ─────────────────────────────────────────────────────────────
//
//    private String extractDescription(JsonNode node) {
//        if (node == null || node.isNull()) return null;
//        if (node.isTextual()) return node.asText();
//        StringBuilder sb = new StringBuilder();
//        if (node.has("content")) {
//            node.get("content").forEach(block -> {
//                if (block.has("content")) {
//                    block.get("content").forEach(seg -> {
//                        if (seg.has("text")) sb.append(seg.get("text").asText());
//                    });
//                    sb.append("\n");
//                }
//            });
//        }
//        String result = sb.toString().trim();
//        return result.isEmpty() ? node.toString() : result;
//    }
//
//    private UserSummary mapUser(UserSummary u) {
//        if (u == null) return null;
//        UserSummary s = new UserSummary();
//        s.setAccountId(u.getAccountId());
//        s.setDisplayName(u.getDisplayName());
//        s.setEmailAddress(u.getEmailAddress());
//        s.setActive(true);
//        return s;
//    }
//
//    private LocalDateTime toLocalDateTime(Date d) {
//        return d == null
//                ? null
//                : Instant.ofEpochMilli(d.getTime()).atZone(ZoneOffset.UTC).toLocalDateTime();
//    }
//
//    private LocalDate toLocalDate(Date d) {
//        return d == null
//                ? null
//                : Instant.ofEpochMilli(d.getTime()).atZone(ZoneOffset.UTC).toLocalDate();
//    }
//}
