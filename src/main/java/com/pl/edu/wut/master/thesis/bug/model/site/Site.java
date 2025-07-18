package com.pl.edu.wut.master.thesis.bug.model.site;

import com.pl.edu.wut.master.thesis.bug.model.user.UserCredential;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "sites")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@ToString(exclude = "credential")
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "site_id")
    private Long id;

    @Column(name = "sitename", nullable = false)
    private String sitename;

    private String baseUrl;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "credential_id", nullable = false)
    private UserCredential credential;

    @Column(name = "added_at", nullable = false, updatable = false)
    private OffsetDateTime addedAt;
}
