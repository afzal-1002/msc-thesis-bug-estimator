package com.pl.edu.wut.master.thesis.bug.model.ai.deepseek;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class DeepSeekChatRequest {
    private String model;
    private List<DeepSeekMessage> messages;
    private boolean stream;
}