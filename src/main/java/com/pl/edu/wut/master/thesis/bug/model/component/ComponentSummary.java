package com.pl.edu.wut.master.thesis.bug.model.component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pl.edu.wut.master.thesis.bug.model.user.UserSummary;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ComponentSummary {
    private String self;
    private String id;
    private String name;
    private String description;
    private UserSummary lead;
    private String assigneeType;
}


