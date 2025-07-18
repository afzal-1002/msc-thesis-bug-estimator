package com.pl.edu.wut.master.thesis.bug.service.implementation;

import com.pl.edu.wut.master.thesis.bug.configuration.JiraClientConfiguration;
import com.pl.edu.wut.master.thesis.bug.dto.issue.response.IssueResponse;
import com.pl.edu.wut.master.thesis.bug.dto.issue.response.IssueResponseSummary;
import com.pl.edu.wut.master.thesis.bug.dto.project.ProjectSummary;
import com.pl.edu.wut.master.thesis.bug.dto.user.usersession.SessionContext;
import com.pl.edu.wut.master.thesis.bug.exception.ProjectNotFoundException;
import com.pl.edu.wut.master.thesis.bug.exception.ResourceNotFoundException;
import com.pl.edu.wut.master.thesis.bug.model.issue.*;
import com.pl.edu.wut.master.thesis.bug.model.issuetype.IssueType;
import com.pl.edu.wut.master.thesis.bug.model.issuetype.IssueTypeReference;
import com.pl.edu.wut.master.thesis.bug.model.project.Project;
import com.pl.edu.wut.master.thesis.bug.model.project.ProjectReference;
import com.pl.edu.wut.master.thesis.bug.model.user.User;
import com.pl.edu.wut.master.thesis.bug.model.user.UserReference;
import com.pl.edu.wut.master.thesis.bug.model.user.UserSummary;
import com.pl.edu.wut.master.thesis.bug.repository.IssueRepository;
import com.pl.edu.wut.master.thesis.bug.service.*;
import com.pl.edu.wut.master.thesis.bug.mapper.IssueMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IssueServiceImplementation implements IssueService {

    private final JiraIssueService jiraIssueService;

    private final JiraClientConfiguration jiraClientConfiguration;
    private final IssueTypeService issueTypeService;
    private final ProjectService projectService;
    private final IssueRepository issueRepository;
    private final IssueMapper issueMapper;
    private final UserService userService;

    @Override
    public IssueResponse createIssue(CreateIssueRequest request) {
        // 0. pull out your three references from the incoming DTO
        CreateIssueFields   inFields   = request.getFields();
        ProjectReference    projRef    = inFields.getProject();
        IssueTypeReference  typeRef    = inFields.getIssuetype();
        UserReference       userRef    = inFields.getAssignee();

        // 1. resolve each reference to a real entity (or throw)
        Project project = projectService.find(ProjectReference.of(projRef.getId(), projRef.getKey()))
                .orElseThrow(() ->  new ProjectNotFoundException( "No project with id="
                        + projRef.getId() + " or key=" + projRef.getKey() ));

        IssueType issueType = issueTypeService.find(IssueTypeReference.of(typeRef.getId(), typeRef.getName()))
                .orElseThrow(() -> new ResourceNotFoundException( "No issue‑type with id=" +
                        typeRef.getId() + " or name=" + typeRef.getName()));

        User assignee = userService.find(UserReference.of(userRef.getId(), userRef.getUsername()))
                .orElseThrow(() -> new ResourceNotFoundException( "No user with id=" +
                        userRef.getId() + " or username=" + userRef.getUsername()));

        // 2. inject resolved IDs/keys back into the Jira request payload
        inFields.setProject(project.toReference());
        inFields.getIssuetype().setId(issueType.getId());
        inFields.getAssignee().setId(assignee.getAccountId());

        // 3. call out to Jira
        IssueResponseSummary newIssue = jiraIssueService.createIssue(request);
        IssueResponse    jiraRaw  = jiraIssueService.getIssueByKey(newIssue.getKey());

        // 4. map to your DB entity, attach assignee, and save
        Issue entity = issueMapper.toIssueEntity(jiraRaw, project, issueType);
        // assume Issue.assignee is of type UserSummary—map accordingly:
        UserSummary usrSummary = UserSummary.builder()
                .accountId(assignee.getAccountId())
                .username(assignee.getUsername())
                .displayName(assignee.getDisplayName())
                .emailAddress(assignee.getEmailAddress())
                .build();
        entity.setAssignee(usrSummary);
        Issue saved = issueRepository.save(entity);

        ProjectSummary projSummary = ProjectSummary.builder()
                .id(String.valueOf(project.getId()))
                .key(project.getKey())
                .name(project.getName())
                .build();

        // 5. now build your response using the top‑level IssueFields class
        IssueFields outFields = IssueFields.builder()
                .project(projSummary)
                .issuetype(issueType)      // still your IssueType object
                .assignee(usrSummary)
                .summary(saved.getSummary())
                .duedate(saved.getDueDate())
                .labels(saved.getLabels())
                .build();

        return IssueResponse.builder()
                .id(saved.getId().toString())
                .key(saved.getKey())
                .self(jiraRaw.getSelf())
                .fields(outFields)
                .build();
    }


    @Override
    @Transactional(readOnly = true)
    public List<IssueResponse> listAllIssues() {
        synchronizeAllIssues();
        List<Issue> issues = issueRepository.findAll();
        return issues.stream()
                .map(issueMapper::toIssueResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public IssueResponse getIssueByKey(String issueKey) {

        SessionContext sessionContext = jiraClientConfiguration.loadSessionContext();
        // 1. Fetch issue from Jira
        IssueResponse jiraIssue = jiraIssueService.getIssueByKey(issueKey);

        Optional<Project> optionalProject = projectService.findById(sessionContext.getProjectId());

        Project  project = optionalProject.orElseThrow(() -> new
                ProjectNotFoundException("Project not found" ));

        Optional<IssueType> optionalIssueType = issueTypeService.findByName(jiraIssue.getFields().getIssuetype().getName());

        IssueType issueType = optionalIssueType.orElseThrow(() -> new ResourceNotFoundException("Issue type not found"));


        // 2. Map to Entity
        Issue issueEntity = issueMapper.toIssueEntity(jiraIssue,project, issueType);

        // 3. Save or update locally
        issueRepository.save(issueEntity);

        return jiraIssue;
    }

    @Override
    public List<IssueResponse> listIssuesForProject(String projectKey) {

        // 1. Fetch issues for project from Jira
        IssueSearchResult jiraIssuesSummary = jiraIssueService.listIssuesForProject(projectKey);

        List<IssueRecord> jiraIssues = jiraIssuesSummary.getIssues();

        // 2. Map each IssueRecord → Issue entity
        List<Issue> issueEntities = jiraIssues.stream().map(issueMapper::toIssueEntity)
                .collect(Collectors.toList());

        // 3. Save all locally
        issueRepository.saveAll(issueEntities);

        // 4. Map saved entities → IssueResponse DTOs
        return issueEntities.stream().map(issueMapper::toIssueResponse)
                .collect(Collectors.toList());
    }


    @Override
    public IssueResponse updateIssue(String issueKey, CreateIssueRequest request) {
        // 1. Update issue in Jira
        IssueResponse jiraUpdated = jiraIssueService.updateIssue(issueKey, request);

        // 2. Map to Entity
        Issue entity = issueMapper.toIssueEntity(jiraUpdated.getFields());

        // 3. Save locally
        issueRepository.save(entity);

        return jiraUpdated;
    }

    @Override
    public void synchronizeAllIssues() {
        // 1. Fetch all issues from Jira
        IssueSearchResult jiraResult = jiraIssueService.fetchAllIssuesCurrentProject();
        List<IssueRecord> jiraIssueRecords = jiraResult.getIssues();

        // 2. Convert Jira issues to Entities
        List<Issue> jiraEntities = jiraIssueRecords.stream()
                .map(issueMapper::toIssueEntity).toList();

        // 3. Build a map of Jira issues by ID for quick lookup
        Map<Long, Issue> jiraMap = jiraEntities.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(Issue::getId, e -> e));

        // 4. Fetch local issues
        List<Issue> localIssues = issueRepository.findAll();

        // 5. Identify local issues no longer in Jira → delete them
        List<Issue> toDelete = new ArrayList<>();
        for (Issue local : localIssues) {
            if (!jiraMap.containsKey(local.getId())) {
                toDelete.add(local);
            }
        }
        issueRepository.deleteAll(toDelete);

        // 6. Save or update all Jira issues into the DB
        issueRepository.saveAll(jiraEntities);
    }

    @Override
    public List<Issue> findByProjectId(Long projectId) {
        // 1. Find local project entity
        Project localProject = projectService.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + projectId));

        // 2. Fetch all issues from JIRA for this project
        String projectKey = localProject.getKey();
        IssueSearchResult jiraIssuesResult = jiraIssueService.listIssuesForProject(projectKey);

        List<IssueRecord> jiraIssueRecords = jiraIssuesResult.getIssues();

        // 3. Build a Map of Jira issues for fast lookup
        Map<String, IssueRecord> jiraMap = jiraIssueRecords.stream()
                .collect(Collectors.toMap(IssueRecord::getId, i -> i));

        // 4. Fetch all local issues for this project
        List<Issue> localIssues = issueRepository.findAllByProject_Id(projectId);

        List<Issue> toDelete = new ArrayList<>();

        // 5. Identify local issues not in JIRA and mark them for deletion
        for (Issue local : localIssues) {
            if (!jiraMap.containsKey(String.valueOf(local.getId()))) {
                toDelete.add(local);
            }
        }

        // 6. Delete missing issues from local DB
        if (!toDelete.isEmpty()) {
            issueRepository.deleteAll(toDelete);
        }

        // 7. Map and upsert all Jira issues into local DB
        List<Issue> updatedOrInserted = new ArrayList<>();

        for (IssueRecord jiraRecord : jiraIssueRecords) {
            Issue entity = issueMapper.toIssueEntity(jiraRecord, localProject);
            updatedOrInserted.add(entity);
        }

        issueRepository.saveAll(updatedOrInserted);

        return updatedOrInserted;
    }
}
