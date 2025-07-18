package com.pl.edu.wut.master.thesis.bug.dto.user;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    private Long id;
    private String accountId;
    private String loginName;
    private String displayName;
    private String emailAddress;
    private String username;
    private int sessionTimeout;
    private boolean isActive;

}