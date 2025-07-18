package com.pl.edu.wut.master.thesis.bug.model.common;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorEmbeddable { // Renamed for clarity

    @Column(name = "author_self_url", length = 500)
    private String self;

    @Column(name = "author_account_id")
    private String accountId;

    @Column(name = "author_email_address")
    private String emailAddress;

    @Embedded // Embed the AvatarUrlsEmbeddable here
    private AvatarUrlsEmbeddable avatarUrls;

    @Column(name = "author_display_name")
    private String displayName;

    @Column(name = "author_active")
    private Boolean active;

    @Column(name = "author_time_zone")
    private String timeZone;

    @Column(name = "author_account_type")
    private String accountType;
}