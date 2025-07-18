package com.pl.edu.wut.master.thesis.bug.model.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pl.edu.wut.master.thesis.bug.model.common.AvatarUrls;
import jakarta.persistence.*;
import lombok.*;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Embeddable
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class UserSummary {
    @Column(name = "self", columnDefinition = "TEXT")
    private String self;
    @Column(name = "account_id")
    private String accountId;
    @Column(name = "account_type")
    private String accountType;
    @Column(name = "email_address")
    private String emailAddress;
    @Column(name = "username")
    private String username;
    @Column(name = "displayName")
    private String displayName;
    @Column(name = "active")
    private Boolean active; // CHANGED: From boolean to Boolean
    @Column(name = "time_zone")
    private String timeZone;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "url48x48", column = @Column(name = "avatar_url_48x48")),
            @AttributeOverride(name = "url24x24", column = @Column(name = "avatar_url_24x24")),
            @AttributeOverride(name = "url16x16", column = @Column(name = "avatar_url_16x16")),
            @AttributeOverride(name = "url32x32", column = @Column(name = "avatar_url_32x32"))
    })
    private AvatarUrls avatarUrls;
}