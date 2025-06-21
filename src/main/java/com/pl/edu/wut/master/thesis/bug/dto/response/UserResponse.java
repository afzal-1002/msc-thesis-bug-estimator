package com.pl.edu.wut.master.thesis.bug.dto.response;


import lombok.*;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UserResponse {
    private Long id;
    private String accountId;
    private String username;
    private String email;
    private List<ProjectResponse> projects;
}