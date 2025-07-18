package com.pl.edu.wut.master.thesis.bug.service;

import com.pl.edu.wut.master.thesis.bug.model.issuetype.IssueType;
import com.pl.edu.wut.master.thesis.bug.model.issuetype.CreateIssueTypeRequest;
import com.pl.edu.wut.master.thesis.bug.model.issuetype.CreateIssueTypeResponse;
import com.pl.edu.wut.master.thesis.bug.model.issuetype.IssueTypeReference;

import java.util.List;
import java.util.Optional;

public interface IssueTypeService {
    CreateIssueTypeResponse createIssueType(CreateIssueTypeRequest request);
    List<IssueType> syncAllIssueTypes();
    Optional<IssueType> find(IssueTypeReference reference);
    Optional<IssueType> findById(Long id);
    Optional<IssueType> findByName(String name);
    public IssueType findOrCreateIssueType(IssueType jiraIssueType, String issueKey);
    // ─── NEW METHODS ───────────────────────────────────────────────────────
    /** Return all IssueType rows from the local DB. */
    List<IssueType> getAllIssueTypesWUT();

    public List<IssueType> getAllIssueTypesJira();
    /** Look up a single IssueType by its numeric ID (Jira’s ID). */
    IssueType getIssueTypeById(Long issueTypeId);

    IssueType getIssueTypeByName(String issueTypeName);

    /**
     * Fetch all issue‐types that are available in a given Jira project.
     */
    List<IssueType> getAllIssueTypesForProject(Long projectId);

    /**
     * Update an existing issue‐type in Jira (and optionally sync to local DB).
     */
    CreateIssueTypeResponse updateIssueType(Long issueTypeId, CreateIssueTypeRequest request);

    /**
     * Delete an issue‐type in Jira (and optionally in the local DB).
     */
    void deleteIssueType(Long issueTypeId);


}