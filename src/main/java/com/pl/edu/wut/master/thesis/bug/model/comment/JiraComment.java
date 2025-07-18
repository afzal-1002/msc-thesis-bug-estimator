package com.pl.edu.wut.master.thesis.bug.model.comment;

import com.fasterxml.jackson.annotation.*;
import com.pl.edu.wut.master.thesis.bug.model.common.Description;
import com.pl.edu.wut.master.thesis.bug.model.common.Visibility;
import com.pl.edu.wut.master.thesis.bug.model.user.UserSummary;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JiraComment {
    private String self;
    private String id;
    private Integer version;
    private UserSummary author;

    @JsonProperty("body")
    private Description body;
    private OffsetDateTime created;
    private UserSummary updateAuthor;
    private OffsetDateTime updated;
    private Visibility visibility;
}
