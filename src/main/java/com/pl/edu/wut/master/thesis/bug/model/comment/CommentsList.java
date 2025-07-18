package com.pl.edu.wut.master.thesis.bug.model.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentsList {
    private List<CommentResponse> comments;
}