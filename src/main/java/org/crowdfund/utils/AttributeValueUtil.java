package org.crowdfund.utils;

import lombok.NonNull;
import org.crowdfund.exceptions.InternalServiceException;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Base64;
import java.util.Map;

/**
 * util for encode & decode string
 */
public final class AttributeValueUtil {

    private AttributeValueUtil() {

    }

    // Encrypt last evaluated key
    public static String encryptString(@NonNull final String value) {
        try {
            return Base64.getUrlEncoder().encodeToString(value.getBytes());
        } catch (Exception e) {
            //TODO add specific exception
            throw new InternalServiceException("Failed to encrypt " + value + e.getMessage());
        }
    }

    // Decrypt last evaluated key
    public static String decryptLastEvaluatedKey(@NonNull final String encryptedValue) {

        try {
            byte[] decodedBytes = Base64.getUrlDecoder().decode(encryptedValue);
            return new String(decodedBytes);
        } catch (final Exception e) {
            //TODO add specific exception
            throw new InternalServiceException("Failed to decrypt " + encryptedValue + e.getMessage());
        }
    }

    // Deprecate last evaluated key
    public static String deprecateLastEvaluatedKey(String lastEvaluatedKey) {
        return lastEvaluatedKey + "_DEPRECATED";
    }

    public static String getNext(final Map<String, AttributeValue> lastEvaluatedKey) {
        if (lastEvaluatedKey == null) {
            return "";
        }

        String[] keys = (lastEvaluatedKey.keySet()).toArray(new String[0]);

        return encryptString(lastEvaluatedKey.get(keys[0]) + ","
                + lastEvaluatedKey.get(keys[1]).s());
    }
}
