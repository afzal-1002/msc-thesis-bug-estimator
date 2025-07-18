package com.pl.edu.wut.master.thesis.bug.dto.user;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class UserCredentialResponse {
    private Long id;
    private Long userId;
    private String jiraUsername;
    private OffsetDateTime createdAt;
    private OffsetDateTime expiresAt;
}