//package com.pl.edu.wut.master.thesis.bug.service.implementation;
//
//import com.pl.edu.wut.master.thesis.bug.dto.request.CommentRequest;
//import com.pl.edu.wut.master.thesis.bug.dto.request.IssueRequest;
//import com.pl.edu.wut.master.thesis.bug.dto.response.CommentResponse;
//import com.pl.edu.wut.master.thesis.bug.dto.response.IssueResponse;
//import com.pl.edu.wut.master.thesis.bug.exception.ResourceNotFoundException;
//import com.pl.edu.wut.master.thesis.bug.mapper.IssueMapper;
//import com.pl.edu.wut.master.thesis.bug.model.issue.Issue;
//import com.pl.edu.wut.master.thesis.bug.repository.IssueRepository;
//import com.pl.edu.wut.master.thesis.bug.service.IssueService;
//import com.pl.edu.wut.master.thesis.bug.service.JiraClientService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class IssueServiceImplementation implements IssueService {
//
//    private final IssueRepository issueRepository;
//    private final IssueMapper issueMapper;
//    private final JiraClientService jiraClientService;
//
//    @Override
//    public List<IssueResponse> getAllIssues() {
//        return issueRepository.findAll().stream()
//                .map(issueMapper::toResponse)
//                .toList();
//    }
//
//    @Override
//    public IssueResponse getIssueById(Long issueId) {
//        Issue issue = issueRepository.findById(issueId)
//                .orElseThrow(() -> new ResourceNotFoundException("Issue not found: " + issueId));
//        return issueMapper.toResponse(issue);
//    }
//
//    @Override
//    public IssueResponse getIssueByKey(String issueKey) {
//        Issue issue = issueRepository.findByIssueKey(issueKey).orElseThrow(
//                () -> new ResourceNotFoundException("Issue not found: " + issueKey)
//        );
//        if (issue == null) {throw new ResourceNotFoundException("Issue not found: " + issueKey);}
//        return issueMapper.toResponse(issue);
//    }
//
//    @Override
//    public List<IssueResponse> getIssuesForProject(String projectKey) {
//        String jql = "project=" + projectKey;
//        return jiraClientService.fetchIssuesByJql(jql);
//    }
//
//    @Override
//    public IssueResponse createIssue(IssueRequest request) {
//        Issue issue = issueMapper.toEntity(request);
//        issueRepository.save(issue);
//        IssueResponse jiraDto = jiraClientService.addIssue(request);
//        issueRepository.save(issueMapper.toEntity(jiraDto));
//        return jiraDto;
//    }
//
//    @Override
//    public IssueResponse updateIssue(String issueKey, IssueRequest request) {
//        Optional<Issue> existing = issueRepository.findByIssueKey(issueKey);
//        if (existing.isEmpty()) { throw new ResourceNotFoundException("Issue not found: " + issueKey);}
//        IssueResponse jiraDto = jiraClientService.updateIssueInJira(request);
//        issueRepository.save(issueMapper.toEntity(jiraDto));
//        return jiraDto;
//    }
//
//    @Override
//    public void deleteIssue(String issueKey) {
//        Optional<Issue> existing = issueRepository.findByIssueKey(issueKey);
//        if (existing.isEmpty()) { throw new ResourceNotFoundException("Issue not found: " + issueKey);}
//        jiraClientService.deleteIssueInJira(
//                IssueRequest.builder().issueKey(issueKey).build()
//        );
//        issueRepository.deleteById(existing.get().getId());
//    }
//
//    @Override
//    public IssueResponse getIssueForEstimation(String issueKey) {
//        return jiraClientService.fetchIssue(IssueRequest.builder().issueKey(issueKey).build());
//    }
//
//    @Override
//    public List<Object> getIssueChangelog(String issueKey) {
//        return Collections.singletonList(jiraClientService.fetchIssueChangelog(issueKey));
//    }
//
//    @Override
//    public Object getIssueEditMeta(String issueKey) {
//        return jiraClientService.fetchIssueEditMeta(issueKey);
//    }
//
//    @Override
//    public List<Object> getIssueTransitions(String issueKey) {
//        return Collections.singletonList(jiraClientService.fetchIssueTransitions(issueKey));
//    }
//
//    @Override
//    public void transitionIssue(String issueKey, Object transitionRequest) {
//        // unsafe cast, but satisfies the compiler
//        @SuppressWarnings("unchecked")
//        Map<String,Object> payload = (Map<String,Object>) transitionRequest;
//        jiraClientService.transitionIssueInJira(issueKey, payload);
//    }
//
//
//    @Override
//    public void assignIssue(String issueKey, Object assigneeRequest) {
//        //noinspection unchecked
//        jiraClientService.assignIssueInJira(issueKey,
//                (Map<String, Object>) assigneeRequest);
//    }
//
//    @Override
//    public List<IssueResponse> searchIssues(String jql) {
//        return jiraClientService.fetchIssuesByJql(jql);
//    }
//
//    @Override
//    public CommentResponse addEstimationComment(String issueKey, String body) {
//        CommentRequest.CommentRequestBuilder builder = CommentRequest.builder();
//        builder.issueId(Long.valueOf(issueKey));
//        builder.body(body);
//        CommentRequest req = builder.build();
//        return jiraClientService.addComment(req);
//    }
//}
