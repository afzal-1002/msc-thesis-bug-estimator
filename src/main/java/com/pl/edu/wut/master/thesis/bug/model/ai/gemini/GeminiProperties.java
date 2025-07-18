package com.pl.edu.wut.master.thesis.bug.model.ai.gemini;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component @Data
@ConfigurationProperties(prefix = "gemini.api")
public class GeminiProperties {
    private String baseUrl;
    private String key;
    private String model;
}