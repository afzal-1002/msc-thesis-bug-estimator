package com.pl.edu.wut.master.thesis.bug.service;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Value;


import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

@Service
public class TokenCryptoService {

    private final String secretKey;
    private final String salt;

    public TokenCryptoService(
            @Value("${app.encryption.secret}") String secretKey,
            @Value("${app.encryption.salt}") String salt) {
        if (secretKey == null || secretKey.isEmpty() || salt == null || salt.isEmpty()) {
            throw new IllegalStateException("Encryption secret and salt must be configured in application.properties");
        }
        this.secretKey = secretKey;
        this.salt = salt;
    }

    public String encryptToken(String token) {
        try {
            // 1. Derive AES key
            SecretKeySpec secretKeySpec = getSecretKeySpec();

            // 2. Initialize AES-CBC encryption
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] iv = cipher.getIV();
            byte[] encryptedBytes = cipher.doFinal(token.getBytes(StandardCharsets.UTF_8));

            // 3. Combine IV + encrypted data in Base64
            return Base64.getEncoder().encodeToString(iv) + ":" +
                    Base64.getEncoder().encodeToString(encryptedBytes);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException("Encryption failed: " + e.getMessage(), e);
        }
    }

    public String decryptToken(String encryptedToken) {
        try {
            String[] parts = encryptedToken.split(":");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid token format (expected IV:EncryptedData)");
            }

            // 1. Extract IV and encrypted data
            byte[] iv = Base64.getDecoder().decode(parts[0]);
            byte[] encryptedData = Base64.getDecoder().decode(parts[1]);

            // 2. Derive the same AES key
            SecretKeySpec secretKeySpec = getSecretKeySpec();

            // 3. Decrypt
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(iv));
            byte[] decryptedBytes = cipher.doFinal(encryptedData);

            return new String(decryptedBytes, StandardCharsets.UTF_8);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
            throw new RuntimeException("Decryption failed: " + e.getMessage(), e);
        }
    }

    private SecretKeySpec getSecretKeySpec() {
        try {
            // Key derivation using PBKDF2
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            return new SecretKeySpec(tmp.getEncoded(), "AES");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Key generation failed: " + e.getMessage(), e);
        }
    }
}