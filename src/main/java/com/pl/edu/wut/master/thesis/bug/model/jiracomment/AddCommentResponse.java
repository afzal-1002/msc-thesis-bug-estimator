package com.pl.edu.wut.master.thesis.bug.model.jiracomment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pl.edu.wut.master.thesis.bug.model.user.UserSummary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Map;

// Main Response Class
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddCommentResponse {
    private String self;
    private String id;
    private UserSummary author;
    private Body body;
    private UserSummary updateAuthor;
    private OffsetDateTime created;
    private OffsetDateTime updated;

    private VisibilityResponse visibility;

    @JsonProperty("jsdPublic")
    private Boolean jsdPublic;
}