package com.pl.edu.wut.master.thesis.bug.model.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Embeddable // Mark this class as embeddable for JPA
public class AvatarUrls {
    @JsonProperty("48x48")
    @Column(name = "url_48x48", length = 500)
    private String url48x48;

    @JsonProperty("24x24")
    @Column(name = "url_24x24", length = 500)
    private String url24x24;

    @JsonProperty("16x16")
    @Column(name = "url_16x16", length = 500)
    private String url16x16;

    @JsonProperty("32x32")
    @Column(name = "url_32x32", length = 500)
    private String url32x32;
}