package com.pl.edu.wut.master.thesis.bug.model.user;

import com.pl.edu.wut.master.thesis.bug.model.site.Site;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_credentials")
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@ToString(exclude = {"user", "sites"})
public class UserCredential {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credential_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    /** Atlassian account name (email) */
    @Column(name = "jira_username", nullable = false)
    private String username;

    /** Encrypted at rest via JPA AttributeConverter */
    @Column(name = "jira_token", nullable = false, length = 512)
    private String token;

    /** Base-URL of this credential’s “home” site */
    @Column(name = "jira_base_url", nullable = false)
    private String jiraBaseUrl;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(name = "expires_at")
    private OffsetDateTime expiresAt;

    @OneToMany( mappedBy = "credential",
            cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Site> sites = new HashSet<>();
}
