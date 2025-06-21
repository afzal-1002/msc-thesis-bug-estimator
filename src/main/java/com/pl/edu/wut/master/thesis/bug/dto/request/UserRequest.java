package com.pl.edu.wut.master.thesis.bug.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {

    private String username;
    private String password;
    private String email;
}