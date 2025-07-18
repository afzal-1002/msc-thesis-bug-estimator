package com.pl.edu.wut.master.thesis.bug.contoller;


import com.pl.edu.wut.master.thesis.bug.model.ai.deepseek.DeepSeekChatRequest;
import com.pl.edu.wut.master.thesis.bug.model.ai.deepseek.DeepSeekChatResponse;
import com.pl.edu.wut.master.thesis.bug.model.ai.deepseek.DeepSeekMessage;
import com.pl.edu.wut.master.thesis.bug.service.DeepSeekService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deepseek")
@RequiredArgsConstructor
public class DeepSeekController {

    private final DeepSeekService deepSeekService;

    @PostMapping("/chat")
    public ResponseEntity<DeepSeekChatResponse> chat(@RequestBody DeepSeekChatRequest request) {
        DeepSeekChatResponse result = deepSeekService.chat(request);
        return ResponseEntity.ok(result);
    }
}
