package com.pl.edu.wut.master.thesis.bug.model.ai;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatChoice {
    private int index;
    private ChatMessage message;
    private String finishReason;

}
