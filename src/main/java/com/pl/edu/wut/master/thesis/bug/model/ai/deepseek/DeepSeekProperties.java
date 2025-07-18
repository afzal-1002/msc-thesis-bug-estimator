package com.pl.edu.wut.master.thesis.bug.model.ai.deepseek;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "deepseek.api")
public class DeepSeekProperties {
    private String baseUrl;
    private String key;
}
