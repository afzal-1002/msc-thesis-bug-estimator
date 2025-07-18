package com.pl.edu.wut.master.thesis.bug.service.implementation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pl.edu.wut.master.thesis.bug.exception.GeminiException;
import com.pl.edu.wut.master.thesis.bug.model.ai.ChatCompletionResponse;
import com.pl.edu.wut.master.thesis.bug.model.ai.ChatChoice;
import com.pl.edu.wut.master.thesis.bug.model.ai.ChatMessage;
import com.pl.edu.wut.master.thesis.bug.model.ai.JiraCommentDto;
import com.pl.edu.wut.master.thesis.bug.model.ai.gemini.GeminiProperties;
import com.pl.edu.wut.master.thesis.bug.service.GeminiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeminiServiceImplementation implements GeminiService {

    private final RestTemplate restTemplate;
    private final GeminiProperties geminiProperties;
    private final ObjectMapper objectMapper;

    @Override
    public ChatCompletionResponse fetchResponseForComments(List<JiraCommentDto> comments) {
        // 1) combine all comment bodies into one prompt (or adjust as you like)
        String prompt = comments.stream()
                .map(JiraCommentDto::getBody)
                .collect(Collectors.joining("\n\n"));

        // 2) build a Map‑based body for generateContent
        Map<String,Object> part       = Map.of("text", prompt);
        Map<String,Object> content    = Map.of("parts", List.of(part));
        Map<String,Object> requestMap = Map.of("contents", List.of(content));

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-goog-api-key",    geminiProperties.getKey());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String,Object>> entity = new HttpEntity<>(requestMap, headers);

        // 3) correct URL for your model
        String url = String.format(
                "%s/v1beta/models/%s:generateContent",
                geminiProperties.getBaseUrl(),
                geminiProperties.getModel()
        );

        try {
            // 4) call and get raw JSON
            ResponseEntity<String> resp = restTemplate.exchange(
                    url, HttpMethod.POST, entity, String.class
            );

            JsonNode root = objectMapper.readTree(resp.getBody());
            JsonNode candidates = root.path("candidates");

            // 5) map to your ChatCompletionResponse
            List<ChatChoice> choices = new ArrayList<>();
            for (JsonNode cand : candidates) {
                ChatMessage msg = new ChatMessage();
                msg.setRole("assistant");
                // join all part.texts
                String text = Stream.of(cand.path("content").path("parts"))
                        .flatMap(p -> {
                            List<String> ts = new ArrayList<>();
                            p.forEach(partNode -> ts.add(partNode.path("text").asText()));
                            return ts.stream();
                        })
                        .collect(Collectors.joining("\n"));
                msg.setContent(text);

                ChatChoice choice = new ChatChoice();
                choice.setIndex(cand.path("index").asInt());
                choice.setMessage(msg);
                choices.add(choice);
            }

            ChatCompletionResponse out = new ChatCompletionResponse();
            out.setModel(geminiProperties.getModel());
            out.setChoices(choices);
            // no token‐usage info available here
            return out;

        } catch (HttpClientErrorException e) {
            log.error("Gemini returned {}: {}", e.getStatusCode(), e.getResponseBodyAsString(), e);
            throw new GeminiException("Gemini client error: " + e.getStatusCode());

        } catch (ResourceAccessException e) {
            log.error("I/O error calling Gemini", e);
            throw new GeminiException("Timeout calling Gemini");

        } catch (RestClientException | java.io.IOException e) {
            log.error("Unexpected error", e);
            throw new GeminiException("Unexpected error calling Gemini");
        }
    }
}
