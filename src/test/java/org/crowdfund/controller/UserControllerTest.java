//package org.crowdfund.controller;
//
//import org.crowdfund.controller.UserController;
//import org.crowdfund.models.UserDTO;
//import org.crowdfund.pojo.UserCreateRequest;
//import org.crowdfund.pojo.UserSaveDTO;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.ResponseEntity;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class UserControllerTest {
//
//    @Mock
//    private UserService userService;
//
//    @InjectMocks
//    private UserController userController;
//
//    @Test
//    public void testGetUserById() {
//        // Arrange
//        String userId = "test-user-id";
//        UserDTO expectedUserDTO = new UserDTO();
//        when(userService.getUserById(userId)).thenReturn(expectedUserDTO);
//
//        // Act
//        ResponseEntity<UserDTO> response = userController.getUserById(userId);
//
//        // Assert
//        assertEquals(expectedUserDTO, response.getBody());
//    }
//
//    @Test
//    public void testAddUser() {
//        // Arrange
//        UserCreateRequest userCreateRequest = new UserCreateRequest();
//        userCreateRequest.setEmailId("test-email-id");
//        userCreateRequest.setName("test-name");
//
//        // Act
//        ResponseEntity<Object> response = userController.addUser(userCreateRequest);
//
//        // Assert
//        assertEquals("User created successfully", response.getBody());
//        verify(userService).save(any(UserSaveDTO.class));
//    }
//
//    @Test
//    public void testBuildUserSaveDTO() {
//        // Arrange
//        UserCreateRequest userCreateRequest = new UserCreateRequest();
//        userCreateRequest.setEmailId("test-email-id");
//        userCreateRequest.setName("test-name");
//
//        // Act
//        UserSaveDTO userSaveDTO = userController.buildUserSaveDTO(userCreateRequest);
//
//        // Assert
//        assertEquals("test-email-id", userSaveDTO.getEmailId());
//        assertEquals("test-name", userSaveDTO.getName());
//    }
//}
