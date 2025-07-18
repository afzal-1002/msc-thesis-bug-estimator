package com.pl.edu.wut.master.thesis.bug.model.jiracomment;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VisibilityResponse {
    private String type;
    private String value;
    private String identifier;
}
