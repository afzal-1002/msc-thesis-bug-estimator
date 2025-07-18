package com.pl.edu.wut.master.thesis.bug.model.issuetype;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor @Builder
public class CreateIssueTypeRequest {
    private String name;
    private String type;
    private String description;
}