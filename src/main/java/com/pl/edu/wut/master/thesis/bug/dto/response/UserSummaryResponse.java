package com.pl.edu.wut.master.thesis.bug.dto.response;

import com.pl.edu.wut.master.thesis.bug.model.user.UserSummary;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSummaryResponse  {
    private String accountId;
    private String emailAddress;
    private String displayName;
}