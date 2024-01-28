package com.gooseApi.passwordHashing;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class PasswordHashing {
    private static final int SALT_LENGTH = 16;

    public String hashPassword(String password) throws NoSuchAlgorithmException {
        byte[] salt = generateSalt();
        String saltString = Base64.getEncoder().encodeToString(salt);
        String saltedPassword = password + saltString;
        String hashedPassword = hashString(saltedPassword);
        return  saltString + "$" + hashedPassword;
    }

    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    private static String hashString(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashedBytes = md.digest(input.getBytes());
        return Base64.getEncoder().encodeToString(hashedBytes);
    }

    public boolean verifyPassword(String inputPassword, String storedPassword) throws NoSuchAlgorithmException {
        String[] parts = storedPassword.split("\\$");
        String storedSalt = parts[0];
        String storedHash = parts[1];
        String inputWithSalt = inputPassword + storedSalt;
        String hashedInput = hashString(inputWithSalt);
        return hashedInput.equals(storedHash);
    }
}
