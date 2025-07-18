package com.pl.edu.wut.master.thesis.bug.model.jiracomment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pl.edu.wut.master.thesis.bug.dto.comment.CommentResponseWithIssueSummary;
import com.pl.edu.wut.master.thesis.bug.dto.issue.response.IssueResponse;
import com.pl.edu.wut.master.thesis.bug.dto.issue.response.IssueSummaryDto;
import com.pl.edu.wut.master.thesis.bug.dto.project.ProjectSummary;
import com.pl.edu.wut.master.thesis.bug.enums.PriorityEnum;
import com.pl.edu.wut.master.thesis.bug.enums.Status;
import com.pl.edu.wut.master.thesis.bug.enums.SynchronizationStatus;
import com.pl.edu.wut.master.thesis.bug.exception.ResourceNotFoundException;
import com.pl.edu.wut.master.thesis.bug.mapper.ProjectMapper;
import com.pl.edu.wut.master.thesis.bug.model.comment.Comment;
import com.pl.edu.wut.master.thesis.bug.model.common.AvatarUrls;
import com.pl.edu.wut.master.thesis.bug.model.common.Visibility;
import com.pl.edu.wut.master.thesis.bug.model.issue.Issue;
import com.pl.edu.wut.master.thesis.bug.model.issuetype.IssueType;
import com.pl.edu.wut.master.thesis.bug.model.project.Project;
import com.pl.edu.wut.master.thesis.bug.model.user.UserSummary;
import com.pl.edu.wut.master.thesis.bug.repository.CommentRepository;
import com.pl.edu.wut.master.thesis.bug.repository.IssueRepository;
import com.pl.edu.wut.master.thesis.bug.service.IssueTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JiraCommentService {

    private final RestTemplate restTemplate;
    private final CommentRepository commentRepository;
    private final IssueRepository issueRepository;
    private final ObjectMapper objectMapper;
    private final ProjectMapper projectMapper;
    private final IssueTypeService issueTypeService;


    private String jiraApiBaseUrl = "https://bugresolution.atlassian.net/rest/api/3";
    private String jiraUsername = "01108500@pw.edu.pl";
    private String jiraPassword= "ATATT3xFfGF0AbqX3XAKoCz-detHxlJs3RMe-zapZETh0rKfG2kJtj4dz2LB1GnA6d8OWX46ddnzYVluE-2zbq91p2B4ywePNNBjvIY5ARryu9Gny6Wy2qHpsnkCn9Ke2_yqQR2j1nd8VP2CXuv50NI7YbNI1QGHT_H4V4iJu8a25yq694xaP5o=3DF35505";

    private HttpHeaders createAuthHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String auth = jiraUsername + ":" + jiraPassword;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.US_ASCII));
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set("Authorization", authHeader);
        return headers;
    }

    // --- Local DB Methods ---

    public Optional<Comment> getCommentByIdFromDb(String commentId) {
        return commentRepository.findById(commentId);
    }

    public Comment createCommentInDb(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment updateCommentInDb(Comment comment) {
        return commentRepository.save(comment);
    }

    public void deleteCommentInDb(String commentId) {
        commentRepository.deleteById(commentId);
    }

    public List<Comment> getAllCommentsFromDb() {
        return commentRepository.findAll();
    }

    // --- Jira API Methods ---
    public AddCommentResponse getCommentByKeyAndCommentIdFromJira(String issueKey, String commentId) {
        String url = UriComponentsBuilder.fromHttpUrl(jiraApiBaseUrl).path("/issue/").path(issueKey)
                .path("/comment/")
                .path(commentId)
                .toUriString();
        HttpEntity<Void> entity = new HttpEntity<>(createAuthHeaders());
        try {
            ResponseEntity<AddCommentResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, AddCommentResponse.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                    return null;
                }
                throw new RuntimeException("Failed to get comment by ID from Jira: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error communicating with Jira API for getting comment by ID", e);
        }
    }

    public IssueCommentsResponse getCommentsByKeyFromJira(String issueKey) {
        String url = UriComponentsBuilder.fromHttpUrl(jiraApiBaseUrl).path("/issue/").path(issueKey)
                .path("/comment")
                .toUriString();
        HttpEntity<Void> entity = new HttpEntity<>(createAuthHeaders());
        try {
            ResponseEntity<IssueCommentsResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, IssueCommentsResponse.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                    return null;
                }
                throw new RuntimeException("Failed to get all comments for issue from Jira: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error communicating with Jira API for getting all comments", e);
        }
    }

    public AddCommentResponse saveCommentToJira(String issueKey, AddCommentRequest request) {
        String url = UriComponentsBuilder.fromHttpUrl(jiraApiBaseUrl).path("/issue/").path(issueKey)
                .path("/comment")
                .toUriString();

        HttpEntity<AddCommentRequest> entity = new HttpEntity<>(request, createAuthHeaders());

        try {
            ResponseEntity<AddCommentResponse> response = restTemplate.postForEntity(url, entity, AddCommentResponse.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                throw new RuntimeException("Failed to add comment to Jira: " + response.getStatusCode() + " - " + response.getBody());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error communicating with Jira API for creating comment", e);
        }
    }

    public AddCommentResponse updateCommentToJira(String issueKey, String commentId, AddCommentRequest request) {
        String url = UriComponentsBuilder.fromHttpUrl(jiraApiBaseUrl).path("/issue/").path(issueKey)
                .path("/comment/")
                .path(commentId)
                .toUriString();
        HttpEntity<AddCommentRequest> entity = new HttpEntity<>(request, createAuthHeaders());
        try {
            ResponseEntity<AddCommentResponse> response = restTemplate.exchange(url, HttpMethod.PUT, entity, AddCommentResponse.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                throw new RuntimeException("Failed to update comment in Jira: " + response.getStatusCode() + " - " + response.getBody());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error communicating with Jira API for updating comment", e);
        }
    }

    public void deleteCommentFromJira(String issueKey, String commentId) {
        String url = UriComponentsBuilder.fromHttpUrl(jiraApiBaseUrl)
                .path("/issue/")
                .path(issueKey)
                .path("/comment/")
                .path(commentId)
                .toUriString();
        HttpEntity<Void> entity = new HttpEntity<>(createAuthHeaders());
        try {
            restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
        } catch (HttpClientErrorException.NotFound e) {
            System.err.println("Comment with ID " + commentId + " not found in Jira for deletion.");
        } catch (Exception e) {
            throw new RuntimeException("Error communicating with Jira API for deleting comment", e);
        }
    }

    public CommentsByIdsResponse getListOfCommentsFromJiraByCommentsIds(List<String> commentIds) {
        String url = UriComponentsBuilder.fromHttpUrl(jiraApiBaseUrl).path("/comment/list").toUriString();
        CommentListRequest requestBody = new CommentListRequest(commentIds);
        HttpEntity<CommentListRequest> entity = new HttpEntity<>(requestBody, createAuthHeaders());
        try {
            ResponseEntity<String> rawResponse = restTemplate.postForEntity(url, entity, String.class);
            if (rawResponse.getStatusCode().is2xxSuccessful()) {
                return objectMapper.readValue(rawResponse.getBody(), CommentsByIdsResponse.class);
            } else {
                throw new RuntimeException("Failed to get comments by IDs from Jira: " + rawResponse.getStatusCode() + " - " + rawResponse.getBody());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error communicating with Jira API for getting comments by IDs", e);
        }
    }

    // --- Synchronization Methods ---
    public List<Comment> synchronizeCommentsFromLocalToJira(String issueKey) {
        fetchAndSaveIssueIfNotExist(issueKey);
        syncCommentsFromLocalToJira();
        return commentRepository.findByIssueKey(issueKey);
    }

    public void syncCommentsFromLocalToJira() {
        List<Comment> pendingComments = commentRepository.findBySyncStatus(SynchronizationStatus.PENDING);
        for (Comment comment : pendingComments) {
            try {
                fetchAndSaveIssueIfNotExist(comment.getIssueKey());
                AddCommentRequest request = new AddCommentRequest();
                if (comment.getBody() != null) {
                    request.setBody(objectMapper.convertValue(objectMapper.readTree(comment.getBody()), Body.class));
                } else {
                    request.setBody(new Body("doc", 1, List.of()));
                }
                request.setVisibility(comment.getVisibility());
                AddCommentResponse jiraResponse = saveCommentToJira(comment.getIssueKey(), request);
                comment.setId(jiraResponse.getId());
                comment.setSyncStatus(SynchronizationStatus.SYNCED);
                commentRepository.save(comment);
            } catch (Exception e) {
                comment.setSyncStatus(SynchronizationStatus.FAILED);
                commentRepository.save(comment);
            }
        }

        List<Comment> updatedComments = commentRepository.findBySyncStatus(SynchronizationStatus.UPDATED);
        for (Comment comment : updatedComments) {
            if (comment.getId() != null) {
                try {
                    fetchAndSaveIssueIfNotExist(comment.getIssueKey());
                    AddCommentRequest request = new AddCommentRequest();
                    if (comment.getBody() != null) {
                        request.setBody(objectMapper.convertValue(objectMapper.readTree(comment.getBody()), Body.class));
                    } else {
                        request.setBody(new Body("doc", 1, List.of()));
                    }
                    request.setVisibility(comment.getVisibility());
                    updateCommentToJira(comment.getIssueKey(), comment.getId(), request);
                    comment.setSyncStatus(SynchronizationStatus.SYNCED);
                    commentRepository.save(comment);
                } catch (Exception e) {
                    comment.setSyncStatus(SynchronizationStatus.FAILED);
                    commentRepository.save(comment);
                }
            }
        }
    }

// ... (inside JiraCommentService.java)

// ... (inside JiraCommentService.java)

    @Transactional
    public List<CommentResponseWithIssueSummary> synchronizeCommentsFromJiraToLocal(String issueKey) {
        // Ensure the Issue and its related entities are fetched
        Issue issue = fetchAndSaveIssueIfNotExist(issueKey);
        boolean issueWasUpdated = false;

        IssueCommentsResponse jiraCommentsResponse = null;
        try {
            jiraCommentsResponse = getCommentsByKeyFromJira(issueKey);
        } catch (Exception e) {
            return new ArrayList<>();
        }

        List<AddCommentResponse> jiraComments = jiraCommentsResponse != null && jiraCommentsResponse.getComments() != null ?
                jiraCommentsResponse.getComments() : List.of();
        Set<String> jiraCommentIds = jiraComments.stream()
                .map(AddCommentResponse::getId)
                .collect(Collectors.toSet());

        List<Comment> localSyncedComments = commentRepository.findByIssueKeyAndSyncStatus(issueKey, SynchronizationStatus.SYNCED);
        Set<String> localSyncedCommentIds = localSyncedComments.stream()
                .map(Comment::getId)
                .collect(Collectors.toSet());

        for (AddCommentResponse jiraComment : jiraComments) {
            Comment localComment = commentRepository.findById(jiraComment.getId()).orElse(null);
            if (localComment == null) {
                // New comment found in Jira, save it locally
                mapAndSave(jiraComment, issueKey);
                issueWasUpdated = true; // Mark as updated
            } else {
                // Existing comment, check for updates
                if (jiraComment.getUpdated() != null && localComment.getUpdated() != null &&
                        jiraComment.getUpdated().isAfter(localComment.getUpdated())) {
                    mapAndSave(jiraComment, issueKey);
                    issueWasUpdated = true; // Mark as updated
                }
            }
        }

        for (String localId : localSyncedCommentIds) {
            if (!jiraCommentIds.contains(localId)) {
                // Comment was deleted in Jira, delete it locally
                Comment commentToDelete = commentRepository.findById(localId).orElse(null);
                if (commentToDelete != null) {
                    commentRepository.delete(commentToDelete);
                    issueWasUpdated = true; // Mark as updated
                }
            }
        }

        // --- NEW LOGIC ---
        // If any changes were made (new comment, updated comment, or deleted comment),
        // update the parent issue's syncStatus to UPDATED.
        if (issueWasUpdated) {
            issue.setSyncStatus(SynchronizationStatus.UPDATED);
            issueRepository.save(issue);
        }
        // --- END NEW LOGIC ---

        // Retrieve and map to DTO to prevent LazyInitializationException
        List<Comment> finalSyncedComments = commentRepository.findByIssueKey(issueKey);
        return finalSyncedComments.stream()
                .map(comment -> CommentResponseWithIssueSummary.builder()
                        .id(comment.getId())
                        .self(comment.getSelf())
                        .authorDisplayName(comment.getAuthor() != null ? comment.getAuthor().getDisplayName() : "N/A")
                        .created(comment.getCreated())
                        .updated(comment.getUpdated())
                        .issueKey(comment.getIssueKey())
                        .issueSummary(IssueSummaryDto.builder()
                                .key(issue.getKey())
                                .summary(issue.getSummary())
                                .status(issue.getStatus() != null ? issue.getStatus().name().replace("_", " ") : null)
                                .build())
                        .build())
                .collect(Collectors.toList());
    }

    // ... (inside JiraCommentService.java)

// ... (inside JiraCommentService.java)

    @Transactional
    public CommentResponseWithIssueSummary saveCommentInJiraAndDb(String issueKey, AddCommentRequest request) {
        // 1. Ensure the parent issue exists in the local database before saving a comment.
        Issue issue = fetchAndSaveIssueIfNotExist(issueKey);

        // 2. Save comment to Jira
        AddCommentResponse jiraResponse = saveCommentToJira(issueKey, request);

        // 3. Map Jira response to local Comment entity and save to DB
        Comment savedComment = mapAndSave(jiraResponse, issueKey);

        // --- FIX: Map the saved JPA entity to a DTO before returning ---
        CommentResponseWithIssueSummary.CommentResponseWithIssueSummaryBuilder builder = CommentResponseWithIssueSummary.builder();
        builder.id(savedComment.getId());
        builder.self(savedComment.getSelf());
        builder.authorDisplayName(savedComment.getAuthor() != null ? savedComment.getAuthor().getDisplayName() : "N/A");
        builder.created(savedComment.getCreated());
        builder.updated(savedComment.getUpdated());
        builder.issueKey(savedComment.getIssueKey());
        builder.issueSummary(IssueSummaryDto.builder()
                .key(issue.getKey())
                .summary(issue.getSummary())
                .status(issue.getStatus() != null ? issue.getStatus().name().replace("_", " ") : null)
                .build());
        return builder
                .build();
    }

    @Transactional
    public Comment updateCommentInJiraAndDb(String issueKey, String commentId, AddCommentRequest request) {
        // This line is the critical fix.
        fetchAndSaveIssueIfNotExist(issueKey);

        // 1. Update comment in Jira
        AddCommentResponse jiraResponse = updateCommentToJira(issueKey, commentId, request);

        // 2. Map updated Jira response to local Comment entity and save to DB
        return mapAndSave(jiraResponse, issueKey);
    }

    @Transactional
    public void deleteCommentInJiraAndDb(String issueKey, String commentId) {
        // 1. Delete comment from Jira
        deleteCommentFromJira(issueKey, commentId);

        // 2. Delete comment from local DB
        deleteCommentInDb(commentId);
    }


    // --- Internal Helper Methods ---
    // ... (inside JiraCommentService.java)
    private Issue fetchAndSaveIssueIfNotExist(String issueKey) {
        return issueRepository.findByKey(issueKey)
                .orElseGet(() -> {
                    try {
                        IssueResponse jiraIssue = getIssueFromJira(issueKey);
                        if (jiraIssue != null) {
                            Issue.IssueBuilder builder = Issue.builder();
                            builder.id(Long.valueOf(jiraIssue.getId()));
                            builder.key(jiraIssue.getKey());
                            builder.summary(jiraIssue.getFields() != null ? jiraIssue.getFields().getSummary() : null);

                            String jiraStatusName = jiraIssue.getFields() != null && jiraIssue.getFields().getStatus() != null ?
                                    jiraIssue.getFields().getStatus().getName() : null;
                            Status statusEnum = null;
                            if (jiraStatusName != null) {
                                try {
                                    String enumName = jiraStatusName.toUpperCase().replace(" ", "_");
                                    statusEnum = Status.valueOf(enumName);
                                } catch (IllegalArgumentException e) {
                                    throw new RuntimeException("Invalid status name: " + e.getMessage());
                                }
                            }
                            builder.status(statusEnum);

                            if (jiraIssue.getFields() != null) {
                                // --- FIX: Pass issueKey to the helper method ---
                                if (jiraIssue.getFields().getIssuetype() != null) {
                                    IssueType localIssueType = issueTypeService.findOrCreateIssueType(jiraIssue.getFields().getIssuetype(), issueKey);
                                    builder.issueType(localIssueType);
                                } else {
                                    builder.issueType(null);
                                }
                                // --- END OF FIX ---

                                if (jiraIssue.getFields().getProject() != null) {
                                    ProjectSummary projectSummary = jiraIssue.getFields().getProject();
                                    Project project =  projectMapper.toProjectEntity(projectSummary);
                                    builder.project(project);
                                }
                                builder.description(String.valueOf(jiraIssue.getFields().getDescription()));
                                String jiraPriorityName = jiraIssue.getFields().getPriority() != null ? jiraIssue.getFields().getPriority().getName() : null;
                                if (jiraPriorityName != null) {
                                    try {
                                        builder.priority(PriorityEnum.valueOf(jiraPriorityName.toUpperCase()));
                                    } catch (IllegalArgumentException e) {
                                        System.err.println("Warning: Unknown priority '" + jiraPriorityName + "' from Jira for issue " + issueKey + ". Setting priority to null.");
                                        builder.priority(null);
                                    }
                                } else {
                                    builder.priority(null);
                                }
                                builder.reporter(mapUserSummary(jiraIssue.getFields().getReporter()));
                                builder.assignee(mapUserSummary(jiraIssue.getFields().getAssignee()));
                                builder.createdAt(jiraIssue.getFields().getCreated().toLocalDateTime());
                                builder.updatedAt(jiraIssue.getFields().getUpdated().toLocalDateTime());
                                builder.dueDate(jiraIssue.getFields().getDuedate());

                                if (jiraIssue.getFields().getResolutionDate() != null) {
                                    builder.resolvedAt(jiraIssue.getFields().getResolutionDate().toLocalDateTime());
                                } else {
                                    builder.resolvedAt(null);
                                }

                                builder.environment(jiraIssue.getFields().getEnvironment());
                                builder.parentKey(jiraIssue.getFields().getParent() != null ? jiraIssue.getFields().getParent().getKey() : null);
                                builder.self(jiraIssue.getSelf());
                            }
                            builder.syncStatus(SynchronizationStatus.SYNCED);
                            Issue newIssue = builder.build();
                            Issue savedIssue = issueRepository.save(newIssue);
                            return savedIssue;
                        } else {
                            throw new RuntimeException("Issue " + issueKey + " not found in Jira and cannot be synchronized locally.");
                        }
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to synchronize issue " + issueKey + " for comment linking: " + e.getMessage(), e);
                    }
                });
    }

    private IssueResponse getIssueFromJira(String issueKey) {
        String url = UriComponentsBuilder.fromHttpUrl(jiraApiBaseUrl)
                .path("/issue/")
                .path(issueKey)
                .toUriString();
        HttpEntity<Void> entity = new HttpEntity<>(createAuthHeaders());
        try {
            ResponseEntity<IssueResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, IssueResponse.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                    return null;
                }
                throw new RuntimeException("Failed to get issue from Jira: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error communicating with Jira API for issue", e);
        }
    }

// ... (inside JiraCommentService.java)

    private Comment mapAndSave(AddCommentResponse jiraComment, String issueKey) {
        Comment comment = commentRepository.findById(jiraComment.getId()).orElse(new Comment());

        Issue issue = issueRepository.findByKey(issueKey).orElseGet(() -> {
                    // This block should ideally not be reached, but is a safe fallback.
                    System.err.println("Fallback: Issue " + issueKey + " not found, attempting to re-sync.");
                    return fetchAndSaveIssueIfNotExist(issueKey);
                });

        comment.setIssue(issue);
        comment.setIssueKey(issueKey);
        comment.setId(jiraComment.getId());

        if (jiraComment.getBody() != null) {
            try {
                String bodyJson = objectMapper.writeValueAsString(jiraComment.getBody());
                comment.setBody(bodyJson);
            } catch (Exception exception) {
                throw new ResourceNotFoundException("Error in Body " + exception.getMessage());
            }
        }
        comment.setCreated(jiraComment.getCreated());
        comment.setUpdated(jiraComment.getUpdated());
        comment.setSelf(jiraComment.getSelf());
        comment.setJsdPublic(jiraComment.getJsdPublic());
        if (jiraComment.getAuthor() != null) {
            comment.setAuthor(mapUserSummary(jiraComment.getAuthor()));
        }
        if (jiraComment.getUpdateAuthor() != null) {
            comment.setUpdateAuthor(mapUserSummary(jiraComment.getUpdateAuthor()));
        }
        if (jiraComment.getVisibility() != null) {
            Visibility localVisibility = new Visibility();
            localVisibility.setType(jiraComment.getVisibility().getType());
            localVisibility.setValue(jiraComment.getVisibility().getValue());
            localVisibility.setIdentifier(jiraComment.getVisibility().getIdentifier());
            comment.setVisibility(localVisibility);
        }
        return commentRepository.save(comment);
    }

    private UserSummary mapUserSummary(UserSummary jiraUser) {
        if (jiraUser == null) {
            return null;
        }
        UserSummary userSummary = new UserSummary();
        userSummary.setSelf(jiraUser.getSelf());
        userSummary.setAccountId(jiraUser.getAccountId());
        userSummary.setEmailAddress(jiraUser.getEmailAddress());
        userSummary.setDisplayName(jiraUser.getDisplayName());
        userSummary.setActive(jiraUser.getActive());
        userSummary.setUsername(null);
        userSummary.setTimeZone(jiraUser.getTimeZone());
        userSummary.setAccountType(jiraUser.getAccountType());

        if (jiraUser.getAvatarUrls() != null) {
            AvatarUrls avatarUrls = jiraUser.getAvatarUrls();
            AvatarUrls localAvatarUrls = new AvatarUrls();
            localAvatarUrls.setUrl48x48(avatarUrls.getUrl48x48());
            localAvatarUrls.setUrl24x24(avatarUrls.getUrl24x24());
            localAvatarUrls.setUrl16x16(avatarUrls.getUrl16x16());
            localAvatarUrls.setUrl32x32(avatarUrls.getUrl32x32());
            userSummary.setAvatarUrls(localAvatarUrls);
        }
        return userSummary;
    }

    private Comment mapJiraCommentToLocalComment(AddCommentResponse jiraComment, String issueKey) {
        Comment localComment = new Comment();
        localComment.setId(jiraComment.getId());
        localComment.setIssueKey(issueKey);
        localComment.setSyncStatus(SynchronizationStatus.SYNCED);
        localComment.setCreated(jiraComment.getCreated());
        localComment.setUpdated(jiraComment.getUpdated());
        if (jiraComment.getBody() != null) {
            try {
                localComment.setBody(objectMapper.writeValueAsString(jiraComment.getBody()));
            } catch (Exception e) {}
        }
        if (jiraComment.getAuthor() != null) {
            localComment.setAuthor(mapUserSummary(jiraComment.getAuthor()));
        }
        return localComment;
    }

    // --- Display Methods ---
    public List<CommentResponseWithIssueSummary> getCommentsWithSummarizedIssue(String issueKey) {
        List<Comment> comments = commentRepository.findByIssueKey(issueKey);
        Optional<Issue> optionalIssue = issueRepository.findByKey(issueKey);
        Issue issue = optionalIssue.orElse(null);

        return comments.stream()
                .map(comment -> {
                    CommentResponseWithIssueSummary.CommentResponseWithIssueSummaryBuilder dtoBuilder = CommentResponseWithIssueSummary.builder()
                            .id(comment.getId())
                            .self(comment.getSelf())
                            .authorDisplayName(comment.getAuthor() != null ? comment.getAuthor().getDisplayName() : "N/A")
                            .created(comment.getCreated())
                            .updated(comment.getUpdated())
                            .issueKey(comment.getIssueKey());

                    if (issue != null) {
                        dtoBuilder.issueSummary(IssueSummaryDto.builder()
                                .key(issue.getKey())
                                .summary(issue.getSummary())
                                .status(issue.getStatus() != null ? issue.getStatus().name().replace("_", " ") : null)
                                .build());
                    }
                    return dtoBuilder.build();
                })
                .collect(Collectors.toList());
    }

    // --- Comparison Methods ---
    public List<Comment> getMismatchedCommentsBetweenJiraAndLocalDb(String issueKey) {
        List<Comment> mismatchedComments = new ArrayList<>();
        List<Comment> localComments = commentRepository.findByIssueKey(issueKey);
        Map<String, Comment> localCommentsMap = localComments.stream()
                .collect(Collectors.toMap(Comment::getId, Function.identity(), (existing, replacement) -> existing));

        IssueCommentsResponse jiraCommentsResponse = null;
        try {
            jiraCommentsResponse = getCommentsByKeyFromJira(issueKey);
        } catch (Exception e) {
            localComments.stream()
                    .filter(c -> c.getSyncStatus() == SynchronizationStatus.SYNCED)
                    .forEach(mismatchedComments::add);
            return mismatchedComments;
        }

        List<AddCommentResponse> jiraComments = jiraCommentsResponse != null && jiraCommentsResponse.getComments() != null ?
                jiraCommentsResponse.getComments() : List.of();
        Map<String, AddCommentResponse> jiraCommentsMap = jiraComments.stream()
                .collect(Collectors.toMap(AddCommentResponse::getId, Function.identity(), (existing, replacement) -> existing));

        for (Comment localComment : localComments) {
            if (localComment.getSyncStatus() == SynchronizationStatus.SYNCED && !jiraCommentsMap.containsKey(localComment.getId())) {
                mismatchedComments.add(localComment);
            }
        }

        for (AddCommentResponse jiraComment : jiraComments) {
            Comment localComment = localCommentsMap.get(jiraComment.getId());
            if (localComment == null) {
                mismatchedComments.add(mapJiraCommentToLocalComment(jiraComment, issueKey));
            } else {
                if (jiraComment.getUpdated() != null && localComment.getUpdated() != null &&
                        jiraComment.getUpdated().isAfter(localComment.getUpdated())) {
                    mismatchedComments.add(localComment);
                }
                try {
                    String localBodyJson = localComment.getBody();
                    String jiraBodyJson = objectMapper.writeValueAsString(jiraComment.getBody());
                    if (!Objects.equals(localBodyJson, jiraBodyJson)) {
                        mismatchedComments.add(localComment);
                    }
                } catch (Exception e) {
                    mismatchedComments.add(localComment);
                }
            }
        }
        return mismatchedComments;
    }
}