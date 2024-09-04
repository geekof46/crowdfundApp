package org.crowdfund.dao;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.crowdfund.exceptions.RecordNotFoundException;
import org.crowdfund.exceptions.UserNotFoundException;
import org.crowdfund.models.UserDTO;
import org.crowdfund.models.db.User;
import org.crowdfund.pojo.UserSaveDTO;
import org.crowdfund.utils.ModelConvertor;
import org.springframework.stereotype.Repository;

import static org.crowdfund.models.db.User.EMAIL_ID;

/**
 * Dao layer for User DDB curd operation
 */
@Repository
@Slf4j
public class UserDao {

    private final DBAccessor<User> dynamoDBAccessor;
    private final ModelConvertor modelConvertor;

    /**
     * Instantiates a new User dao.
     *
     * @param dynamoDBAccessor the dynamo db accessor
     * @param modelConvertor   the model convertor
     */
    public UserDao(@NonNull final DBAccessor<User> dynamoDBAccessor,
                   @NonNull final ModelConvertor modelConvertor) {
        this.dynamoDBAccessor = dynamoDBAccessor;
        this.modelConvertor = modelConvertor;
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
     * @param userId the user id
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
