package com.pl.edu.wut.master.thesis.bug.model.comment;

import com.fasterxml.jackson.databind.JsonNode;
import com.pl.edu.wut.master.thesis.bug.model.common.Visibility;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCommentRequest {
    private JsonNode body;
    private Visibility visibility;
}