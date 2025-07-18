package com.pl.edu.wut.master.thesis.bug.dto.user;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterUserRequest {
    @NotBlank String loginName;
    @NotBlank String password;
    @NotBlank String username;
    @NotBlank String token;
    @NotBlank String sitename;
}