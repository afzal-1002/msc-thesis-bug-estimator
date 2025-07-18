package com.pl.edu.wut.master.thesis.bug.repository;

import com.pl.edu.wut.master.thesis.bug.model.issuetype.IssueType;
import com.pl.edu.wut.master.thesis.bug.model.issuetype.IssueTypeSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IssueTypeRepository extends JpaRepository<IssueType, Long> {

    default IssueType findOrCreate(IssueTypeSummary response) {
        long id = Long.parseLong(response.getId());
        return findById(id)
                .orElseGet(() -> {
                    IssueType issueType = new IssueType();
                    issueType.setId((id));
                    issueType.setName(response.getName());
                    issueType.setDescription(response.getDescription());
                    issueType.setIconUrl(response.getIconUrl());
                    issueType.setSubtask(response.isSubtask());
                    issueType.setAvatarId((int) response.getAvatarId());
                    // if you need hierarchyLevel:
                     issueType.setHierarchyLevel(1);
                    issueType.setSelf(response.getSelf());
                    return save(issueType);
                });
    }

    Optional<IssueType> findByName(String name);
    Optional<IssueType> findByDescription(String description);
    Optional<IssueType> findByIconUrl(String iconUrl);
    Optional<IssueType> findBySubtask(Boolean subtask);

    Optional<IssueType> findById(Long id);

}
