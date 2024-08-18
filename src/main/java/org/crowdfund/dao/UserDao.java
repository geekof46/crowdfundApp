package org.crowdfund.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.crowdfund.models.UserDTO;
import org.crowdfund.models.db.Donation;
import org.crowdfund.models.db.User;
import org.crowdfund.utils.ModelConvertor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

import java.util.List;

/**
 * Dao layer for User DDB curd operation
 */
@Repository
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
     * @param userDTO
     */
    public void save(@NonNull final UserDTO userDTO) {
        dynamoDBAccessor.putItem(modelConvertor.convert(userDTO, User.class));
    }

    /**
     * method to get uses by userId
     * @param userId
     * @return instance of User else return null
     */
    public UserDTO getUserById(@NonNull final String userId){
        return modelConvertor.convert(dynamoDBAccessor.getRecordsByPartitionKey(userId), UserDTO.class);
    }

}
