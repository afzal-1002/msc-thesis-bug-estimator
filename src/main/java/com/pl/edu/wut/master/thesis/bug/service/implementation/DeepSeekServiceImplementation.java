package com.pl.edu.wut.master.thesis.bug.service.implementation;

import com.pl.edu.wut.master.thesis.bug.configuration.DeepSeekClientConfiguration;
import com.pl.edu.wut.master.thesis.bug.model.ai.deepseek.DeepSeekChatRequest;
import com.pl.edu.wut.master.thesis.bug.model.ai.deepseek.DeepSeekChatResponse;
import com.pl.edu.wut.master.thesis.bug.service.DeepSeekService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeepSeekServiceImplementation implements DeepSeekService {

    private final DeepSeekClientConfiguration deepSeekClientConfiguration;

    @Override
    public DeepSeekChatResponse chat(DeepSeekChatRequest request) {
        return deepSeekClientConfiguration.chat(request);
    }
}
