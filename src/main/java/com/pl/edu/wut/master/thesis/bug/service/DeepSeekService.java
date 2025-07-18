package com.pl.edu.wut.master.thesis.bug.service;

import com.pl.edu.wut.master.thesis.bug.model.ai.deepseek.DeepSeekChatRequest;
import com.pl.edu.wut.master.thesis.bug.model.ai.deepseek.DeepSeekChatResponse;
import org.springframework.stereotype.Service;

@Service
public interface DeepSeekService {
    DeepSeekChatResponse chat(DeepSeekChatRequest request);
}
