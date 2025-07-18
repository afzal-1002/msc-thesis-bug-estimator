package com.pl.edu.wut.master.thesis.bug.dto.issue.response;


import com.pl.edu.wut.master.thesis.bug.model.issue.IssueFields;
import lombok.*;

/**
 * DTO representing the core fields of a JIRA issue,
 * used both for create/update responses and local lookups.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class IssueResponse {
    private String id;
    private String key;
    private String self;
    private IssueFields fields;
}
