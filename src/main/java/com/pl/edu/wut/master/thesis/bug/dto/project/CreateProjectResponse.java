package com.pl.edu.wut.master.thesis.bug.dto.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pl.edu.wut.master.thesis.bug.model.user.UserSummary;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateProjectResponse {

    private String id;
    private String key;
    private String name;

    @JsonProperty("projectTypeKey")
    private String projectTypeKey;

    private String description;

    @JsonProperty("lead")
    private UserSummary lead;

}