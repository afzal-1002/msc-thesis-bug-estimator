package com.pl.edu.wut.master.thesis.bug.dto.comment;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentListRequest {
    @NotEmpty
    private List<String> ids;
}