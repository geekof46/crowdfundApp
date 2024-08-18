package org.crowdfund.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.crowdfund.exceptions.InternalServiceException;
import org.crowdfund.exceptions.InvalidRequestException;

/**
 * Class created to convert object of one class into another class.
 */
public class ModelConvertor {
    private final ObjectMapper objectMapper;

    public ModelConvertor(@NonNull final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Method to convert object of fromVale to object of toValueType.
     * @param fromValue which is the object to be converted.
     * @param toValueType which is the class which the object should be converted to.
     * @return the resulting object.
     */
    public <T> T convert(@NonNull final Object fromValue, @NonNull final Class<T> toValueType) {
        T convertedObject = null;
        try {
            convertedObject = objectMapper.convertValue(fromValue, toValueType);
        } catch (final IllegalArgumentException e){
            throw new InvalidRequestException("Failed to convert object", e);
        }
        return convertedObject;
    }

    /**
     * Method to deserialize string to object of toValueType.
     * @param fromValue which is the object to be converted.
     * @param toValueType which is the class which the object should be converted to.
     * @return the resulting object.
     */
    public <T> T readValue(@NonNull final String fromValue, @NonNull final TypeReference<T> toValueType) {
        T convertedObject = null;
        try {
            convertedObject = objectMapper.readValue(fromValue, toValueType);
        } catch (final JsonProcessingException e){
            throw new InvalidRequestException("Failed to convert object", e);
        }
        return convertedObject;
    }

    /**
     * Writes the Provided Object in String Format
     * @param object Object to be written as String
     * @return String formatted Object
     */
    public String writeValueAsString(@NonNull final Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (final JsonProcessingException jsonProcessingException) {
            throw new InternalServiceException("JsonProcessingException occurred while writing as String",
                    jsonProcessingException);
        }
    }
}