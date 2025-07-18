package com.pl.edu.wut.master.thesis.bug.contoller;

import com.pl.edu.wut.master.thesis.bug.model.issuetype.IssueType;
import com.pl.edu.wut.master.thesis.bug.model.issuetype.CreateIssueTypeRequest;
import com.pl.edu.wut.master.thesis.bug.model.issuetype.CreateIssueTypeResponse;
import com.pl.edu.wut.master.thesis.bug.service.IssueTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wut/issue-type")
@RequiredArgsConstructor
public class IssueTypeController {

    private final IssueTypeService issueTypeService;

    @PostMapping("")
    public ResponseEntity<CreateIssueTypeResponse> createIssueType(@RequestBody CreateIssueTypeRequest request) {
        CreateIssueTypeResponse response = issueTypeService.createIssueType(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    /**
     * GET  /api/issue-types/sync
     *
     * Synchronize all issue types from Jira into the local DB,
     * then return the full list of saved IssueType entities.
     */
    @GetMapping("/issue-types/sync")
    public ResponseEntity<List<IssueType>> syncAllIssueTypes() {
        List<IssueType> synced = issueTypeService.syncAllIssueTypes();
        return ResponseEntity.ok(synced);
    }



    /** GET /api/issue-types → all local IssueType rows */
    @GetMapping("/issue-types/wut")
    public ResponseEntity<List<IssueType>> getAllIssueTypesWUT() {
        List<IssueType> list = issueTypeService.getAllIssueTypesWUT();
        return ResponseEntity.ok(list);
    }

    /** GET /api/issue-types → all local IssueType rows */
    @GetMapping("/issue-types/jira")
    public ResponseEntity<List<IssueType>> getAllIssueTypesJira() {
        List<IssueType> list = issueTypeService.getAllIssueTypesJira();
        return ResponseEntity.ok(list);
    }

    /** GET /api/issue-types/{id} → fetch one by its Jira‐ID */
    @GetMapping("/id/{id}")
    public ResponseEntity<IssueType> getIssueTypeById(@PathVariable("id") Long id) {
        IssueType it = issueTypeService.getIssueTypeById(id);
        return ResponseEntity.ok(it);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<IssueType> getIssueTypeByName(@PathVariable("name") String issueTypeName) {
        IssueType it = issueTypeService.getIssueTypeByName(issueTypeName);
        return ResponseEntity.ok(it);
    }


    /**
     * GET /api/issuetypes/project/{projectId}
     * Return all issue types available in the given Jira project.
     */
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<IssueType>> getByProject(@PathVariable Long projectId) {
        List<IssueType> list = issueTypeService.getAllIssueTypesForProject(projectId);
        return ResponseEntity.ok(list);
    }

    /**
     * PUT /api/issuetypes/{issueTypeId}
     * Update an existing issue type in Jira (and mirror changes locally).
     */
    @PutMapping("/{issueTypeId}")
    public ResponseEntity<CreateIssueTypeResponse> update(
            @PathVariable Long issueTypeId,
            @RequestBody CreateIssueTypeRequest request
    ) {
        CreateIssueTypeResponse updated = issueTypeService.updateIssueType(issueTypeId, request);
        return ResponseEntity.ok(updated);
    }

    /**
     * DELETE /api/issuetypes/{issueTypeId}
     * Delete an issue type in Jira (and locally if present).
     */
    @DeleteMapping("/{issueTypeId}")
    public ResponseEntity<Void> delete(@PathVariable Long issueTypeId) {
        issueTypeService.deleteIssueType(issueTypeId);
        return ResponseEntity.noContent().build();
    }
}