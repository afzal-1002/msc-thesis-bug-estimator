package com.pl.edu.wut.master.thesis.bug.contoller;

import com.pl.edu.wut.master.thesis.bug.exception.GeminiException;
import com.pl.edu.wut.master.thesis.bug.model.ai.ChatCompletionResponse;
import com.pl.edu.wut.master.thesis.bug.model.ai.JiraCommentDto;
import com.pl.edu.wut.master.thesis.bug.service.GeminiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gemini")
@RequiredArgsConstructor
public class GeminiController {

    private final GeminiService geminiService;

    @PostMapping("/chat")
    public ResponseEntity<ChatCompletionResponse> chatForComments(
            @RequestBody List<JiraCommentDto> comments
    ) {
        ChatCompletionResponse aiResponse = geminiService.fetchResponseForComments(comments);
        return ResponseEntity.ok(aiResponse);
    }

    @ExceptionHandler(GeminiException.class)
    public ResponseEntity<String> handleGeminiError(GeminiException ex) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body("{\"error\":\"" + ex.getMessage() + "\"}");
    }
}