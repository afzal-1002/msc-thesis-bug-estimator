package com.pl.edu.wut.master.thesis.bug.model.comment;


import com.fasterxml.jackson.databind.JsonNode;
import com.pl.edu.wut.master.thesis.bug.model.user.UserSummary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponse {
    private String id;
    private String self;
    private UserSummary author;
    private JsonNode body;
    private UserSummary updateAuthor;
    private OffsetDateTime created;
    private OffsetDateTime updated;
    private Boolean jsdPublic;
}
