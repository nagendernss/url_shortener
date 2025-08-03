// src/main/java/com/urlshortener/util/ShortIdGenerator.java
package com.urlshortener.util;

import java.security.SecureRandom;

public class ShortIdGenerator {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int DEFAULT_LENGTH = 7;
    private static final SecureRandom random = new SecureRandom();

    public static String generate() {
        return generate(DEFAULT_LENGTH);
    }

    public static String generate(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }

    public static boolean isValidCustomAlias(String alias) {
        if (alias == null || alias.trim().isEmpty()) {
            return false;
        }

        // Check length (3-20 characters)
        if (alias.length() < 3 || alias.length() > 20) {
            return false;
        }

        // Check if contains only alphanumeric characters and hyphens
        return alias.matches("^[a-zA-Z0-9-]+$");
    }
}
