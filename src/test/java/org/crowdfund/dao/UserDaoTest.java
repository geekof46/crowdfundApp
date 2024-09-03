//package org.crowdfund.dao;
//
//import org.crowdfund.exceptions.RecordNotFoundException;
//import org.crowdfund.exceptions.UserNotFoundException;
//import org.crowdfund.models.UserDTO;
//import org.crowdfund.models.db.User;
//import org.crowdfund.pojo.UserSaveDTO;
//import org.crowdfund.utils.ModelConvertor;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class UserDaoTest {
//
//    @Mock
//    private DBAccessor<User> dynamoDBAccessor;
//
//    @Mock
//    private ModelConvertor modelConvertor;
//
//    @InjectMocks
//    private UserDao userDao;
//
//    @Test
//    public void testSave() {
//        // Arrange
//        UserSaveDTO userSaveDTO = new UserSaveDTO();
//        User user = new User();
//        when(modelConvertor.convert(userSaveDTO, User.class)).thenReturn(user);
//
//        // Act
//        userDao.save(userSaveDTO);
//
//        // Assert
//        verify(dynamoDBAccessor).putItem(user, User.EMAIL_ID, User.class);
//    }
//
//    @Test
//    public void testGetUserById() {
//        // Arrange
//        String userId = "test-user-id";
//        User user = new User();
//        when(dynamoDBAccessor.getRecordByPartitionKey(userId, true)).thenReturn(user);
//        when(modelConvertor.convert(user, UserDTO.class)).thenReturn(new UserDTO());
//
//        // Act
//        UserDTO result = userDao.getUserById(userId);
//
//        // Assert
//        assertEquals(new UserDTO(), result);
//    }
//
//    @Test
//    public void testGetUserByIdNotFound() {
//        // Arrange
//        String userId = "test-user-id";
//        when(dynamoDBAccessor.getRecordByPartitionKey(userId, true)).thenThrow(
//                new RecordNotFoundException("Record not found"));
//
//        // Act and Assert
//        assertThrows(UserNotFoundException.class, () -> userDao.getUserById(userId));
//    }
//}
