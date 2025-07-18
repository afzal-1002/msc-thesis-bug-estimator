package com.pl.edu.wut.master.thesis.bug.contoller;


import com.pl.edu.wut.master.thesis.bug.model.user.UserCredential;
import com.pl.edu.wut.master.thesis.bug.repository.UserCredentialRepository;
import com.pl.edu.wut.master.thesis.bug.service.TokenCryptoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/wut/tokens")
@RequiredArgsConstructor
public class TokenController {

    private final TokenCryptoService cryptoService;
    private final UserCredentialRepository credentialRepo;

    // Endpoint to store encrypted token
    @PostMapping("/store")
    public ResponseEntity<String> storeToken(@RequestBody @Valid TokenStorageRequest request) {
        try {
            String encryptedToken = cryptoService.encryptToken(request.getPassword());

            UserCredential credential = new UserCredential();
            credential.setUsername(request.getUsername());
            credential.setToken(encryptedToken);
            credentialRepo.save(credential);

            return ResponseEntity.ok("Token stored successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error encrypting token: " + e.getMessage());
        }
    }

    // Endpoint to retrieve original token
    @GetMapping("/retrieve")
    public ResponseEntity<TokenResponse> retrieveToken(@RequestParam String email) {
        Optional<UserCredential> credential = credentialRepo.findByUsername(email);
        if (credential.isPresent()) {
        try {
            String originalToken = cryptoService.decryptToken(credential.get().getToken());
            return ResponseEntity.ok(new TokenResponse(originalToken));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error decrypting token", e);
        }
    }
        return ResponseEntity.notFound().build();
    }

    // DTOs
    @Data
    public static class TokenStorageRequest {
        @Email
        private String username; // Actually the email
        @NotBlank
        private String password; // The token to encrypt
    }

    @Data
    @AllArgsConstructor
    public static class TokenResponse {
        private String token;
    }
}