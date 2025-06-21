package com.pl.edu.wut.master.thesis.bug.dto.request;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UserSummaryRequest {
    private String accountId;
    private String emailAddress;
    private String displayName;
}