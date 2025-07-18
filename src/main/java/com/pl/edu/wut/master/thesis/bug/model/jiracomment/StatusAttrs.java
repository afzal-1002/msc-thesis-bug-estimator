package com.pl.edu.wut.master.thesis.bug.model.jiracomment;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
class StatusAttrs {
    private String text;
    private String color;
    private String localId;
    private String style;
}
