package com.pl.edu.wut.master.thesis.bug.service;

import com.pl.edu.wut.master.thesis.bug.model.ai.ChatCompletionResponse;
import com.pl.edu.wut.master.thesis.bug.model.ai.JiraCommentDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GeminiService {
    ChatCompletionResponse fetchResponseForComments(List<JiraCommentDto> comments);
}

