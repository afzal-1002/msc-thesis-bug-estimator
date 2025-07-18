package com.pl.edu.wut.master.thesis.bug.dto.comment;

import lombok.Data;
import java.util.List;

/**
 * Mirrors the paginated response from GET /issue/{key}/comment
 */
@Data
public class CommentPage {
    private int startAt;
    private int maxResults;
    private int total;
    private List<CommentResponse> comments;
}
