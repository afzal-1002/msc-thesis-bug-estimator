// com/pl/edu/wut/master/thesis/bug/model/issuetype/IssueType.java

package com.pl.edu.wut.master.thesis.bug.model.issuetype;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "issue_types")
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueType {

    @Id
    private Long   id;

    private String   self;

    @Column(columnDefinition = "TEXT")
    private String   description;

    private String   iconUrl;

    @Column(nullable = false)
    private String   name;

    private String   untranslatedName;        // pulled from JiraIssueType

    private boolean  subtask;

    private Integer  avatarId;                // was Long, switched to Integer

    private int      hierarchyLevel;

    // You can leave projectKey here if you still need it:
    @Column(nullable = false)
    @JsonIgnore
    private String   projectKey;

    @Builder
    @JsonCreator
    public IssueType(
            @JsonProperty("id")               Long   id,
            @JsonProperty("self")             String   self,
            @JsonProperty("description")      String   description,
            @JsonProperty("iconUrl")          String   iconUrl,
            @JsonProperty("name")             String   name,
            @JsonProperty("untranslatedName") String   untranslatedName,
            @JsonProperty("subtask")          boolean  subtask,
            @JsonProperty("avatarId")         Integer  avatarId,
            @JsonProperty("hierarchyLevel")   int      hierarchyLevel
    ) {
        this.id               = id;
        this.self             = self;
        this.description      = description;
        this.iconUrl          = iconUrl;
        this.name             = name;
        this.untranslatedName = untranslatedName;
        this.subtask          = subtask;
        this.avatarId         = avatarId;
        this.hierarchyLevel   = hierarchyLevel;
    }

    public IssueTypeReference toReference() {
        return IssueTypeReference.builder()
                .id(this.id)
                .name(this.name)
                .build();
    }
}
