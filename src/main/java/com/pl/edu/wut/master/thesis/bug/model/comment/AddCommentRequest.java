package com.pl.edu.wut.master.thesis.bug.model.comment;

import com.pl.edu.wut.master.thesis.bug.model.common.Document;
import com.pl.edu.wut.master.thesis.bug.model.common.Visibility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddCommentRequest {
    private String issueId;
    private Document body;
    private Visibility visibility;

}