package com.pl.edu.wut.master.thesis.bug.model.jiracomment;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentsByIdsResponse {
    private Integer maxResults;
    private Integer startAt;
    private Integer total;
    private Boolean isLast;

    // Changed field name from 'comments' to 'values' to match Jira's response
    private List<AddCommentResponse> values = Collections.emptyList();

    // Custom getter for 'values' to ensure a non-null list is always returned
    public List<AddCommentResponse> getValues() {
        return Optional.ofNullable(values).orElse(Collections.emptyList());
    }
}