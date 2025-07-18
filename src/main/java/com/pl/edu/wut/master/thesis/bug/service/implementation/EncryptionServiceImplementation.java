package com.pl.edu.wut.master.thesis.bug.service.implementation;

import com.pl.edu.wut.master.thesis.bug.service.EncryptionService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EncryptionServiceImplementation implements EncryptionService {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public String hashPassword(String rawPassword) {
//        return passwordEncoder.encode(rawPassword);
        return rawPassword;
    }

    @Override
    public boolean matchesPassword(String rawPassword, String hashedPassword) {
//        return passwordEncoder.matches(rawPassword, hashedPassword);

        return rawPassword != null && rawPassword.equals(hashedPassword);
    }

    // these two are no-ops for now
    @Override
    public String encrypt(String plaintext) {
        return plaintext;
    }

    @Override
    public String decrypt(String ciphertext) {
        return ciphertext;
    }
}
