
package com.pl.edu.wut.master.thesis.bug.mapper;

import com.pl.edu.wut.master.thesis.bug.dto.request.UserRequest;
import com.pl.edu.wut.master.thesis.bug.dto.response.UserResponse;
import com.pl.edu.wut.master.thesis.bug.dto.response.UserSummaryResponse;
import com.pl.edu.wut.master.thesis.bug.model.user.User;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.UUID;

@Component
public class UserMapper {

    public User toEntity(UserRequest dto) {
        if (dto == null) return null;

        return User.builder()
                .id(null)  // let JPA generate
                .accountId(UUID.randomUUID().toString())  // generate accountId
                .username(dto.getUsername())
                .password(dto.getPassword())
                .email(dto.getEmail())  // make sure this is included
                .projects(new HashSet<>())  // initialize empty set
                .build();
    }

    public UserResponse toResponse(User u) {
        if (u == null) return null;

        return UserResponse.builder()
                .id(u.getId())
                .accountId(u.getAccountId())
                .username(u.getUsername())
                .email(u.getEmail())
                .build();
    }

    public static UserSummaryResponse mapToResponse(User user) {
        if (user == null) return null;
        return UserSummaryResponse.builder()
                .accountId(user.getAccountId())  // You can map user.getId() if you want
                .emailAddress(user.getUsername())
                .displayName(user.getUsername()) // Adjust to your entity fields
                .build();
    }
}
