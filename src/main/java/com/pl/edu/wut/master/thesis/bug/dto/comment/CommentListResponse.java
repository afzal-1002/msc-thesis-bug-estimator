package com.pl.edu.wut.master.thesis.bug.dto.comment;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentListResponse {
    private List<CommentResponse> comments;
    private int total;
    private int startAt;
    private int maxResults;
}