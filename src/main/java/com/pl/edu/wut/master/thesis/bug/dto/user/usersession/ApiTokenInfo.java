package com.pl.edu.wut.master.thesis.bug.dto.user.usersession;

import lombok.*;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiTokenInfo {
    private String       id;
    private String       label;
    private String       status;
    private OffsetDateTime createdAt;
    private OffsetDateTime expiresAt;
}
