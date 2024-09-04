package org.crowdfund.dao;

import org.crowdfund.exceptions.RecordNotFoundException;
import org.crowdfund.exceptions.UserNotFoundException;
import org.crowdfund.models.UserDTO;
import org.crowdfund.models.db.User;
import org.crowdfund.pojo.UserSaveDTO;
import org.crowdfund.utils.ModelConvertor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDaoTest {
    @Mock
    private DBAccessor<User> dynamoDBAccessor;

    @Mock
    private ModelConvertor modelConvertor;

    private UserDao userDao;

    @BeforeEach
    public void setUp() {
        userDao = new UserDao(dynamoDBAccessor, modelConvertor);
    }

    @Test
    public void testSave() {
        // Arrange
        UserSaveDTO userSaveDTO = getUserSaveDTO();
        final User user = getUser();
        when(modelConvertor.convert(userSaveDTO, User.class)).thenReturn(user);
        doNothing().when(dynamoDBAccessor).putItem(user, User.EMAIL_ID, User.class);

        // Act
        userDao.save(userSaveDTO);

        // Assert
        verify(modelConvertor).convert(userSaveDTO, User.class);
        verify(dynamoDBAccessor).putItem(user, User.EMAIL_ID, User.class);
    }

    @Test
    public void testGetUserById() {
        // Arrange
        final String userId = "USER-1";
        final User user = getUser();
        when(dynamoDBAccessor.getRecordByPartitionKey(userId, true)).thenReturn(user);
        when(modelConvertor.convert(user, UserDTO.class)).thenReturn(getUserDTO());

        // Act
        final UserDTO result = userDao.getUserById(userId);

        // Assert
        assertEquals(userId, result.getUserId());
    }

    @Test
    public void testGetUserByIdNotFound() {
        // Arrange
        final String userId = "test-user-id";
        when(dynamoDBAccessor.getRecordByPartitionKey(userId, true)).thenThrow(
                new RecordNotFoundException("Record not found"));

        // Act and Assert
        assertThrows(UserNotFoundException.class, () -> userDao.getUserById(userId));
    }

    private User getUser() {
        return User.builder()
                .userId("USER-1")
                .name("test-user")
                .emailId("test@gmail.com")
                .build();
    }

    private UserSaveDTO getUserSaveDTO() {
        return UserSaveDTO.builder()
                .userId("USER-1")
                .name("test-user")
                .emailId("test@gmail.com").build();
    }

    private UserDTO getUserDTO() {
        return UserDTO.builder()
                .userId("USER-1")
                .name("test-user")
                .emailId("test@gmail.com").build();
    }
}
