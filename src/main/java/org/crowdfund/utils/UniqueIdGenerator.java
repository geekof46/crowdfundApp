package org.crowdfund.utils;

import lombok.NonNull;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * The type Unique id generator.
 */
public class UniqueIdGenerator {

    private static final SecureRandom random = new SecureRandom();

    /**
     * Generate uuid string.
     *
     * @param prefix the prefix
     * @return the string
     */
    public static String generateUUID(@NonNull final String prefix) {
        byte[] bytes = new byte[4];
        random.nextBytes(bytes);
        return prefix + bytesToHex(bytes).substring(0, 8);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
