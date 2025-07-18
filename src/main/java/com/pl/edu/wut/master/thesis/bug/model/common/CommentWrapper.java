package com.pl.edu.wut.master.thesis.bug.model.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pl.edu.wut.master.thesis.bug.model.user.UserSummary;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentWrapper {
    private List<JiraComment> comments;
    private int startAt;
    private int maxResults;
    private int total;


    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class JiraComment {
        private String id;
        private String self;
        private UserSummary author;
        private Description body;
        private UserSummary updateAuthor;
        private OffsetDateTime created;
        private OffsetDateTime updated;
        private boolean jsdPublic;
    }

}
