package com.pl.edu.wut.master.thesis.bug.model.ai;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {
    private String role;    // e.g. "user"
    private String content; // the comment text

}
