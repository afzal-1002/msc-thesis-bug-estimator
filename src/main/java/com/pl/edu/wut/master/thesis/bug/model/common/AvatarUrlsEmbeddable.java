package com.pl.edu.wut.master.thesis.bug.model.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable // Mark this class as embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AvatarUrlsEmbeddable { // Renamed to avoid confusion with response model

    // Map each URL string to its own column
    @Column(name = "avatar_url_48x48", length = 500)
    @JsonProperty("48x48")
    private String url48x48;

    @Column(name = "avatar_url_24x24", length = 500)
    @JsonProperty("24x24")
    private String url24x24;

    @Column(name = "avatar_url_16x16", length = 500)
    @JsonProperty("16x16")
    private String url16x16;

    @Column(name = "avatar_url_32x32", length = 500)
    @JsonProperty("32x32")
    private String url32x32;
}
