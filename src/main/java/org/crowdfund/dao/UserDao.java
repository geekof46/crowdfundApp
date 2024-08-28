package org.crowdfund.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.crowdfund.exceptions.RecordNotFoundException;
import org.crowdfund.exceptions.UserNotFoundException;
import org.crowdfund.pojo.UserDTO;
import org.crowdfund.models.UserRole;
import org.crowdfund.models.db.User;
import org.crowdfund.utils.ModelConvertor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.List;

import static org.crowdfund.models.db.User.USER_ID;
import static org.crowdfund.models.db.User.USER_ROLE;

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

    public List<User> getUserByRole(@NonNull final UserRole role,
                                    final Integer limit,
                                    final String next) {

        final List<User> users = dynamoDBAccessor.getRecordsByIndexPartitionKey(User.USER_ROLE_TO_USER_ID_INDEX,
                USER_ROLE,
                role.toString(),
                USER_ID,
                next,
                limit);


//        return users.stream().map(record ->
//                modelConvertor.convert(record, UserDTO.class)).collect(Collectors.toList());
        return users;
    }

    /**
     * method to save or update DDB user record
     *
     * @param userDTO
     */
    public void save(@NonNull final UserDTO userDTO) {
        dynamoDBAccessor.putItem(modelConvertor.convert(userDTO, User.class));
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
