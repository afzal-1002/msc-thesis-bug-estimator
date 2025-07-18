package com.pl.edu.wut.master.thesis.bug.configuration;

import com.pl.edu.wut.master.thesis.bug.model.ai.gemini.GeminiProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class GeminiClientConfiguration {

    private final RestTemplate restTemplate;
    private final GeminiProperties geminiProperties;

    /**
     * Calls Gemini's generateContent endpoint.
     */
    public ResponseEntity<String> callGeminiGenerateContent(String promptText) {
        // build the request body
        Map<String, Object> part    = Map.of("text", promptText);
        Map<String, Object> content = Map.of("parts", List.of(part));
        Map<String, Object> body    = Map.of("contents", List.of(content));

        // prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-goog-api-key", geminiProperties.getKey());

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        String url = geminiProperties.getBaseUrl() + "/v1beta/models/gemini-2.0-flash:generateContent";

        try {
            ResponseEntity<String> response = restTemplate.exchange( url,
                    HttpMethod.POST, request, String.class);

            return response;

        } catch (HttpClientErrorException clientException) {
            // 4xx from Gemini → relay status & body
            log.error(
                    "Gemini API client error {}: {}",
                    clientException.getStatusCode(),
                    clientException.getResponseBodyAsString(),
                    clientException
            );
            return ResponseEntity
                    .status(clientException.getStatusCode())
                    .body(clientException.getResponseBodyAsString());

        } catch (ResourceAccessException ioException) {
            // network / timeout
            log.error("Gemini I/O error", ioException);
            return ResponseEntity
                    .status(HttpStatus.GATEWAY_TIMEOUT)
                    .body("{\"error\":\"Timeout calling Gemini API: "
                            + ioException.getMessage() + "\"}");

        } catch (RestClientException restException) {
            // any other RestTemplate issue
            log.error("Unexpected error calling Gemini API", restException);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error calling Gemini API: " + restException.getMessage() + "\"}");
        }
    }
}
