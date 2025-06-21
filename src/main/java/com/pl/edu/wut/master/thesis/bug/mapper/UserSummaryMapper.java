
package com.pl.edu.wut.master.thesis.bug.mapper;

import com.pl.edu.wut.master.thesis.bug.dto.request.UserSummaryRequest;
import com.pl.edu.wut.master.thesis.bug.dto.response.UserSummaryResponse;
import com.pl.edu.wut.master.thesis.bug.model.user.UserSummary;
import org.springframework.stereotype.Component;

@Component
public class UserSummaryMapper {

    public UserSummary toEntity(UserSummaryRequest dto) {
        if (dto == null) return null;
        UserSummary.UserSummaryBuilder builder = UserSummary.builder();
        builder.accountId(dto.getAccountId());
        builder.emailAddress(dto.getEmailAddress());
        builder.displayName(dto.getDisplayName());
        return builder.build();
    }

    public UserSummaryResponse toResponse(UserSummary entity) {
        if (entity == null) return null;
        UserSummaryResponse.UserSummaryResponseBuilder builder = UserSummaryResponse.builder();
        builder.accountId(entity.getAccountId());
        builder.emailAddress(entity.getEmailAddress());
        builder.displayName(entity.getDisplayName());
        return builder.build();
    }

    public UserSummary toEntity(UserSummaryResponse dto) {
        if (dto == null) return null;
        UserSummary.UserSummaryBuilder builder = UserSummary.builder();
        builder.accountId(dto.getAccountId());
        builder.emailAddress(dto.getEmailAddress());
        builder.displayName(dto.getDisplayName());
        return builder.build();
    }

    public  UserSummaryResponse mapToResponse(UserSummary entity) {
        if (entity == null) return null;
        return UserSummaryResponse.builder()
                .accountId(entity.getAccountId())
                .emailAddress(entity.getEmailAddress())
                .displayName(entity.getDisplayName())
                .build();
    }
}
