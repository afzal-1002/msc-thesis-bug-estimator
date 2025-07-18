package com.pl.edu.wut.master.thesis.bug.model.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pl.edu.wut.master.thesis.bug.dto.comment.CommentBody;
import com.pl.edu.wut.master.thesis.bug.model.common.Visibility;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentRequest {
    private CommentBody body;
    private Visibility visibility;
    @JsonProperty("public")
    private Boolean    jsdPublic;
}