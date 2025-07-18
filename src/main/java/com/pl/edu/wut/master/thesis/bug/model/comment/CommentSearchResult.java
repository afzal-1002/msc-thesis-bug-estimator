package com.pl.edu.wut.master.thesis.bug.model.comment;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentSearchResult {
    private int startAt;
    private int maxResults;
    private int total;
    private List<CommentRecord> comments;
}
