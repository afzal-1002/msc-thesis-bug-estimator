package com.pl.edu.wut.master.thesis.bug.dto.user;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String userName;
    private String emailAddress;
    private String displayName;
    private Boolean isActive;
    private String password;
}