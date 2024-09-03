package org.crowdfund.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.crowdfund.exceptions.InvalidRequestException;
import org.crowdfund.exceptions.RecordNotFoundException;
import org.crowdfund.exceptions.UserNotFoundException;
import org.crowdfund.models.UserDTO;
import org.crowdfund.models.db.User;
import org.crowdfund.pojo.UserSaveDTO;
import org.crowdfund.utils.ModelConvertor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Map;

import static org.crowdfund.models.db.User.EMAIL_ID;

/**
 * Dao layer for User DDB curd operation
 */
@Repository
@Slf4j
public class UserDao {

    private final DBAccessor<User> dynamoDBAccessor;
    private final ModelConvertor modelConvertor;

    public UserDao(@NonNull final DynamoDbEnhancedClient dbEnhancedClient,
                   @NonNull final DynamoDbTable<User> dynamoDbTable,
                   @NonNull final ObjectMapper objectMapper) {
        this.dynamoDBAccessor = new DBAccessor<>(dbEnhancedClient, dynamoDbTable);
        this.modelConvertor = new ModelConvertor(objectMapper);
    }

    /**
     * method to save or update DDB user record
     *
     * @param userSaveDTO object to create user entry
     */
    public void save(@NonNull final UserSaveDTO userSaveDTO) {
        dynamoDBAccessor.putItem(modelConvertor.convert(userSaveDTO, User.class),
                EMAIL_ID, User.class);
    }

    /**
     * method to get uses by userId
     *
     * @param userId
     * @return instance of User else return null
     */
    public UserDTO getUserById(@NonNull final String userId) {
        try {
            return modelConvertor.convert(dynamoDBAccessor.getRecordByPartitionKey(userId, true),
                    UserDTO.class);
        } catch (final RecordNotFoundException e) {
            throw new UserNotFoundException("User not found with user Id " + userId);
        }
    }

}
