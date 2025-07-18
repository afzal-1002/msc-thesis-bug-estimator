package com.pl.edu.wut.master.thesis.bug.model.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionAttributes {
    private String issueKey;
    private Long issueId;
    private String username;
    private String loginName;
    private String token;
    private String hostUrl;
    private Long   instanceId;
    private String projectKey;
    private Long   projectId;
    private Long   userId;
    private String accountId;
}
