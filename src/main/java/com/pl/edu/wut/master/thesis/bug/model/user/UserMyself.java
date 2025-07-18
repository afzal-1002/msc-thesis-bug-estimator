package com.pl.edu.wut.master.thesis.bug.model.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pl.edu.wut.master.thesis.bug.model.common.AvatarUrls;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Embeddable
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class UserMyself {
    private String self;
    private String accountId;
    private String accountType;
    private String emailAddress;
    private AvatarUrls avatarUrls;
    private String displayName;
    private boolean active;
    private String timeZone;
    private String locale;
    private Groups groups;
    private ApplicationRoles applicationRoles;
    private String expand;


    @Data  @NoArgsConstructor
    @AllArgsConstructor @Builder
    public static class Groups {
        private int size;
        private List<Object> items = new ArrayList<>();
    }

    @Data  @NoArgsConstructor
    @AllArgsConstructor @Builder
    public static class ApplicationRoles {
        private int size;
        private List<Object> items = new ArrayList<>();

    }

}

