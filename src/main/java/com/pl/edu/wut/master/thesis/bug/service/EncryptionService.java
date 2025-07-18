package com.pl.edu.wut.master.thesis.bug.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public interface EncryptionService {

    String hashPassword(String rawPassword);
    boolean matchesPassword(String rawPassword, String hashedPassword);
    String encrypt(String plaintext);
    String decrypt(String ciphertext);
}