package com.pl.edu.wut.master.thesis.bug.model.comment;

import com.pl.edu.wut.master.thesis.bug.enums.SynchronizationStatus;
import com.pl.edu.wut.master.thesis.bug.model.common.Visibility;
import com.pl.edu.wut.master.thesis.bug.model.issue.Issue;
import com.pl.edu.wut.master.thesis.bug.model.user.UserSummary;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.OffsetDateTime;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

  @Id
  private String id;

  @Column(name = "jira_self_url", columnDefinition = "TEXT")
  private String self;

  @Column(name = "sync_status")
  @Enumerated(EnumType.STRING)
  private SynchronizationStatus syncStatus;

  @Embedded
  @AttributeOverrides({
          @AttributeOverride(name = "self",         column = @Column(name = "author_self",           columnDefinition = "TEXT")),
          @AttributeOverride(name = "accountId",    column = @Column(name = "author_account_id")),
          @AttributeOverride(name = "accountType",  column = @Column(name = "author_account_type")),
          @AttributeOverride(name = "emailAddress", column = @Column(name = "author_email_address")),
          @AttributeOverride(name = "username",     column = @Column(name = "author_username")),
          @AttributeOverride(name = "displayName",  column = @Column(name = "author_display_name")),
          @AttributeOverride(name = "active",       column = @Column(name = "author_active")),
          @AttributeOverride(name = "timeZone",     column = @Column(name = "author_time_zone")),
          @AttributeOverride(name = "avatarUrls.url48x48", column = @Column(name = "author_avatar_url_48x48", length = 500)),
          @AttributeOverride(name = "avatarUrls.url24x24", column = @Column(name = "author_avatar_url_24x24", length = 500)),
          @AttributeOverride(name = "avatarUrls.url16x16", column = @Column(name = "author_avatar_url_16x16", length = 500)),
          @AttributeOverride(name = "avatarUrls.url32x32", column = @Column(name = "author_avatar_url_32x32", length = 500))
  })
  private UserSummary author;

  @Embedded
  @AttributeOverrides({
          @AttributeOverride(name = "self",         column = @Column(name = "update_author_self",           columnDefinition = "TEXT")),
          @AttributeOverride(name = "accountId",    column = @Column(name = "update_author_account_id")),
          @AttributeOverride(name = "accountType",  column = @Column(name = "update_author_account_type")),
          @AttributeOverride(name = "emailAddress", column = @Column(name = "update_author_email_address")),
          @AttributeOverride(name = "username",     column = @Column(name = "update_author_username")),
          @AttributeOverride(name = "displayName",  column = @Column(name = "update_author_display_name")),
          @AttributeOverride(name = "active",       column = @Column(name = "update_author_active")),
          @AttributeOverride(name = "timeZone",     column = @Column(name = "update_author_time_zone")),
          @AttributeOverride(name = "avatarUrls.url48x48", column = @Column(name = "update_author_avatar_url_48x48", length = 500)),
          @AttributeOverride(name = "avatarUrls.url24x24", column = @Column(name = "update_author_avatar_url_24x24", length = 500)),
          @AttributeOverride(name = "avatarUrls.url16x16", column = @Column(name = "update_author_avatar_url_16x16", length = 500)),
          @AttributeOverride(name = "avatarUrls.url32x32", column = @Column(name = "update_author_avatar_url_32x32", length = 500))
  })
  private UserSummary updateAuthor;

  @Column(name = "created_at")
  private OffsetDateTime created;

  @Column(name = "updated_at")
  private OffsetDateTime updated;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "issue_key", referencedColumnName = "issue_key")
  private Issue issue;

  @Column(name = "jira_issue_key")
  private String issueKey;

  @Embedded
  @AttributeOverrides({
          @AttributeOverride(name = "type", column = @Column(name = "visibility_type")),
          @AttributeOverride(name = "value", column = @Column(name = "visibility_value")),
          @AttributeOverride(name = "identifier", column = @Column(name = "visibility_identifier", length = 500))
  })
  private Visibility visibility;

  @Column(name = "body_json", columnDefinition = "TEXT")
  private String body;

  @Column(name = "jsd_public")
  private Boolean jsdPublic;
}