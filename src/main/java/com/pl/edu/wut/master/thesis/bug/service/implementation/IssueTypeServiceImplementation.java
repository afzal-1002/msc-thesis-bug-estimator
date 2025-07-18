package com.pl.edu.wut.master.thesis.bug.service.implementation;

import com.pl.edu.wut.master.thesis.bug.configuration.JiraClientConfiguration;
import com.pl.edu.wut.master.thesis.bug.dto.user.usersession.SessionContext;
import com.pl.edu.wut.master.thesis.bug.exception.CustomException;
import com.pl.edu.wut.master.thesis.bug.exception.ResourceNotFoundException;
import com.pl.edu.wut.master.thesis.bug.model.issuetype.IssueType;
import com.pl.edu.wut.master.thesis.bug.model.issuetype.CreateIssueTypeRequest;
import com.pl.edu.wut.master.thesis.bug.model.issuetype.CreateIssueTypeResponse;
import com.pl.edu.wut.master.thesis.bug.model.issuetype.IssueTypeReference;
import com.pl.edu.wut.master.thesis.bug.repository.IssueTypeRepository;
import com.pl.edu.wut.master.thesis.bug.service.IssueTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class IssueTypeServiceImplementation implements IssueTypeService {

    private final IssueTypeRepository      issueTypeRepository;
    private final JiraClientConfiguration  jiraClientConfiguration;

    @Override
    @Transactional
    public CreateIssueTypeResponse createIssueType(CreateIssueTypeRequest request) {
        SessionContext context = jiraClientConfiguration.loadSessionContext();
        CreateIssueTypeResponse jiraResponse;

        try {
            // Try to create it in Jira
            jiraResponse = jiraClientConfiguration.post("/issuetype", request, CreateIssueTypeResponse.class);
            log.info("Created new issue type in Jira: {}", jiraResponse.getName());

        } catch (HttpClientErrorException exception) {
            if (exception.getStatusCode() == HttpStatus.CONFLICT &&
                    exception.getResponseBodyAsString().contains("already exists")) {

                log.warn("Issue type '{}' already exists; fetching existing", request.getName());

                // Fetch the full list of issue types, find the one matching our name
                CreateIssueTypeResponse[] allTypes = jiraClientConfiguration.get("/issuetype",
                        CreateIssueTypeResponse[].class);

                jiraResponse = Arrays.stream(allTypes)
                        .filter(t -> request.getName().equalsIgnoreCase(t.getName()))
                        .findFirst()
                        .orElseThrow(() -> new IllegalStateException(
                                "Jira reports conflict but cannot find existing type by name"
                        ));

            } else {
                // rethrow anything else
                throw new CustomException("Error while saving issue type", HttpStatus.BAD_REQUEST);
            }
        }

        // At this point jiraResponse has the existing-or-new issue type
        // Map into your JPA entity and persist if you haven’t already saved it
        CreateIssueTypeResponse finalJiraResponse = jiraResponse;
        IssueType issueTypeEntity = issueTypeRepository.findById(jiraResponse.getId())
                .orElseGet(() -> {
                    IssueType.IssueTypeBuilder builder = IssueType.builder();
                    builder.id((finalJiraResponse.getId()));
                    builder.name(finalJiraResponse.getName());
                    builder.description(finalJiraResponse.getDescription());
                    builder.iconUrl(finalJiraResponse.getIconUrl());
                    builder.self(finalJiraResponse.getSelf());
                    builder.subtask(finalJiraResponse.isSubtask());
                    builder.hierarchyLevel(finalJiraResponse.getHierarchyLevel());
                    builder.avatarId(Math.toIntExact(finalJiraResponse.getAvatarId()));
                    IssueType issueType = builder.build();
                    return issueTypeRepository.save(issueType);
                });

        return jiraResponse;
    }

    @Override
    public Optional<IssueType> find(IssueTypeReference reference) {
        if (reference.getId() != null) {
            return issueTypeRepository.findById(reference.getId());
        } else if (reference.getName() != null) {
            return issueTypeRepository.findByName(reference.getName());
        } else {
            throw new IllegalArgumentException(
                    "Issue Type Reference must contain either id or name");
        }
    }

    // ... (inside JiraCommentService.java)
    @Override
    public IssueType findOrCreateIssueType(IssueType jiraIssueType, String issueKey) {
        if (jiraIssueType == null) {
            return null;
        }
        return issueTypeRepository.findById(jiraIssueType.getId())
                .orElseGet(() -> {
                    IssueType newIssueType = new IssueType();
                    newIssueType.setId(jiraIssueType.getId());
                    newIssueType.setName(jiraIssueType.getName());
                    newIssueType.setDescription(jiraIssueType.getDescription());
                    newIssueType.setSubtask(jiraIssueType.isSubtask());

                    // FIX: Extract project key from the issueKey and set it
                    if (issueKey != null && issueKey.contains("-")) {
                        String projectKey = issueKey.substring(0, issueKey.indexOf("-"));
                        newIssueType.setProjectKey(projectKey);
                    } else {
                        System.err.println("Warning: Unable to extract project key from issueKey: " + issueKey);
                        // Handle this case, perhaps by throwing an exception or setting a default value if your schema allows
                    }

                    return issueTypeRepository.save(newIssueType);
                });
    }

    @Override
    public Optional<IssueType> findById(Long id) {
        return issueTypeRepository.findById(id);
    }

    @Override
    public Optional<IssueType> findByName(String name) {
        return issueTypeRepository.findByName(name);
    }


    @Override
    @Transactional
    public List<IssueType> syncAllIssueTypes() {
        // 1) Load session/project context
        SessionContext context = jiraClientConfiguration.loadSessionContext();

        // 2) Fetch all issue types from Jira
        CreateIssueTypeResponse[] jiraTypes = jiraClientConfiguration
                .get("/issuetype", CreateIssueTypeResponse[].class);
        log.info("Fetched {} issue types from Jira", jiraTypes.length);

        List<IssueType> synced = new ArrayList<>(jiraTypes.length);

        // 3) Upsert each one
        for (CreateIssueTypeResponse jiraIssueType : jiraTypes) {
            long jiraId = jiraIssueType.getId();

            IssueType entity = issueTypeRepository.findById(jiraId)
                    .map(existing -> {
                        // update mutable fields
                        existing.setName(jiraIssueType.getName());
                        existing.setDescription(jiraIssueType.getDescription());
                        existing.setIconUrl(jiraIssueType.getIconUrl());
                        existing.setSelf(jiraIssueType.getSelf());
                        existing.setSubtask(jiraIssueType.isSubtask());
                        existing.setHierarchyLevel(jiraIssueType.getHierarchyLevel());
                        existing.setAvatarId(Math.toIntExact(jiraIssueType.getAvatarId()));

                        // projectKey never changes per context
                        return existing;
                    })
                    .orElseGet(() -> {
                        // build new one
                        IssueType.IssueTypeBuilder builder = IssueType.builder();
                        builder.id((jiraId));
                        builder.name(jiraIssueType.getName());
                        builder.description(jiraIssueType.getDescription());
                        builder.iconUrl(jiraIssueType.getIconUrl());
                        builder.self(jiraIssueType.getSelf());
                        builder.subtask(jiraIssueType.isSubtask());
                        builder.hierarchyLevel(jiraIssueType.getHierarchyLevel());
                        builder.avatarId(Math.toIntExact(jiraIssueType.getAvatarId()));
                        return builder.build();
                    });

            // 4) persist (save does insert or update)
            IssueType saved = issueTypeRepository.save(entity);
            synced.add(saved);
        }

        // 5) return the list of upserted entities
        return synced;
    }

    @Override
    @Transactional(readOnly = true)
    public List<IssueType> getAllIssueTypesWUT() {
        return issueTypeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<IssueType> getAllIssueTypesJira() {
        IssueType[] jiraIssueType = jiraClientConfiguration.get("/issuetype", IssueType[].class);
        return Arrays.stream(jiraIssueType).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public IssueType getIssueTypeById(Long issueTypeId) {
        return issueTypeRepository.findById(issueTypeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "IssueType not found: " + issueTypeId));
    }

    @Override
    @Transactional(readOnly = true)
    public IssueType getIssueTypeByName(String issueTypeName) {
        return issueTypeRepository.findByName(issueTypeName)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "IssueType not found: " + issueTypeName));
    }

    @Override
    @Transactional(readOnly = true)
    public List<IssueType> getAllIssueTypesForProject(Long projectId) {
        // Jira’s project‐scoped issuetypes endpoint
        IssueType[] jiraTypes = jiraClientConfiguration
                .get("/issuetype/project?projectId=" + projectId, IssueType[].class);
        return Arrays.asList(jiraTypes);
    }

    @Override
    @Transactional
    public CreateIssueTypeResponse updateIssueType(Long issueTypeId, CreateIssueTypeRequest request) {
        // 1) Push the update to Jira
        CreateIssueTypeResponse updated = jiraClientConfiguration
                .put("/issuetype/" + issueTypeId, request, CreateIssueTypeResponse.class);
        log.info("Updated issue type in Jira: {}", updated.getName());

        // 2) (Optional) mirror change into your local DB
        issueTypeRepository.findById(issueTypeId).ifPresent(entity -> {
            entity.setName(updated.getName());
            entity.setDescription(updated.getDescription());
            entity.setIconUrl(updated.getIconUrl());
            entity.setSubtask(updated.isSubtask());
            entity.setHierarchyLevel(updated.getHierarchyLevel());
            entity.setAvatarId(Math.toIntExact(updated.getAvatarId()));
            issueTypeRepository.save(entity);
        });

        return updated;
    }

    @Override
    @Transactional
    public void deleteIssueType(Long issueTypeId) {
        // 1) Delete in Jira
        jiraClientConfiguration.delete("/issuetype/" + issueTypeId, Void.class);
        log.info("Deleted issue type {} in Jira", issueTypeId);

        // 2) (Optional) Delete from your DB
        if (issueTypeRepository.existsById(issueTypeId)) {
            issueTypeRepository.deleteById(issueTypeId);
            log.info("Deleted issue type {} from local DB", issueTypeId);
        }
    }


}
