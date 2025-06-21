package com.pl.edu.wut.master.thesis.bug.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImportProjectRequest {
private String jiraBaseUrl;
private String jiraUsername;
private String jiraToken;
private String projectKeyOrId;
}
