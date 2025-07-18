package com.pl.edu.wut.master.thesis.bug.model.comment;

import com.fasterxml.jackson.databind.JsonNode;
import com.pl.edu.wut.master.thesis.bug.model.common.Visibility;
import com.pl.edu.wut.master.thesis.bug.model.user.UserSummary;
import lombok.*;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRecord {
    private String id;
    private String self;
    private UserSummary author;
    private UserSummary updateAuthor;
    private JsonNode body;
    private OffsetDateTime created;
    private OffsetDateTime updated;
    private Visibility visibility;
}
