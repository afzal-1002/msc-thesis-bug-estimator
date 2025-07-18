package com.pl.edu.wut.master.thesis.bug.model.comment;

import com.fasterxml.jackson.databind.JsonNode;
import com.pl.edu.wut.master.thesis.bug.model.common.Document;
import com.pl.edu.wut.master.thesis.bug.model.common.Visibility;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCommentRequest {
    @NotNull
    private Document body;
    private Visibility visibility;
}
