package org.crowdfund.service;

import org.crowdfund.dao.UserDao;
import org.crowdfund.exceptions.InvalidRequestException;
import org.crowdfund.exceptions.UserNotFoundException;
import org.crowdfund.models.UserDTO;
import org.crowdfund.pojo.UserSaveDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserService userService;

    @Test
    public void testSave_UserAlreadyExists_ThrowsInvalidRequestException() {
        // Arrange
        UserSaveDTO userSaveDTO = getUserSaveDTO();

        when(userDao.getUserById(userSaveDTO.getEmailId())).thenReturn(new UserDTO());

        // Act and Assert
        assertThrows(InvalidRequestException.class, () -> userService.save(userSaveDTO));
    }

    @Test
    public void testSave_UserDoesNotExist_SavesUser() {
        // Arrange
        UserSaveDTO userSaveDTO = getUserSaveDTO();
        when(userDao.getUserById(userSaveDTO.getEmailId())).thenThrow(new UserNotFoundException("not found"));

        // Act
        userService.save(userSaveDTO);

        // Assert
        verify(userDao).save(userSaveDTO);
    }

    @Test
    public void testGetUserById_ReturnsUserDTO() {
        // Arrange
        String userId = "test-user-id";
        UserDTO expectedUserDTO = getUserDTO();
        when(userDao.getUserById(userId)).thenReturn(expectedUserDTO);

        // Act
        UserDTO actualUserDTO = userService.getUserById(userId);

        // Assert
        assertEquals(expectedUserDTO, actualUserDTO);
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

