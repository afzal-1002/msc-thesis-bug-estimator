package com.pl.edu.wut.master.thesis.bug.mapper;

import com.pl.edu.wut.master.thesis.bug.dto.user.LoginResponse;
import com.pl.edu.wut.master.thesis.bug.model.user.User;
import com.pl.edu.wut.master.thesis.bug.model.user.UserSummary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // ----- Entity → Summary -----
    @Mapping(target = "self",         ignore = true)
    @Mapping(source = "accountId",    target = "accountId")
    @Mapping(source = "username",     target = "username")
    @Mapping(source = "emailAddress", target = "emailAddress")
    @Mapping(source = "displayName",  target = "displayName")
    @Mapping(source = "active",       target = "active")
    UserSummary toUserSummary(User user);

    // ----- Bulk mapping of summaries -----
    List<UserSummary> toUserSummaries(List<User> users);

    // ----- Entity → LoginResponse DTO (multi-source) -----
    @Mapping(source = "user.id",           target = "id")
    @Mapping(source = "user.accountId",    target = "accountId")
    @Mapping(source = "user.logiName",     target = "loginName")
    @Mapping(source = "user.displayName",  target = "displayName")
    @Mapping(source = "user.emailAddress", target = "emailAddress")
    @Mapping(source = "user.username",     target = "username")
    @Mapping(source = "user.active",       target = "isActive")
    @Mapping(source = "sessionTimeout",    target = "sessionTimeout")
    LoginResponse toLoginResponse(User user, int sessionTimeout);

    // ----- Back‐mapping summary → entity (unchanged) -----
    default User fromUserSummary(UserSummary summary) {
        if (summary == null) return null;
        User user = new User();
        user.setAccountId(   summary.getAccountId());
        user.setUsername(    summary.getUsername());
        user.setDisplayName( summary.getDisplayName());
        user.setEmailAddress(summary.getEmailAddress());
        user.setActive(      summary.getActive());
        return user;
    }
}
