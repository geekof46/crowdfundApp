package org.crowdfund.controller;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.crowdfund.exceptions.InvalidRequestException;
import org.crowdfund.models.UserDTO;
import org.crowdfund.pojo.UserCreateRequest;
import org.crowdfund.pojo.UserSaveDTO;
import org.crowdfund.service.UserService;
import org.crowdfund.utils.UniqueIdGenerator;
import org.crowdfund.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Controller class for User
 */
@RestController
@EnableWebMvc
@Slf4j
@RequestMapping("/api/v1")
public class UserController {

    private static final @NonNull String USER_ID_PREFIX = "USER-";
    private final UserService userService;

    /**
     * Instantiates a new User controller.
     *
     * @param userService the user service
     */
    @Autowired
    public UserController(@NonNull final UserService userService) {
        this.userService = userService;
    }

    /**
     * Gets user by id.
     *
     * @param userId the user id
     * @return the user by id
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String userId) {
        return ResponseEntity.ok()
                .body(userService.getUserById(userId));
    }

    /**
     * method to add new user
     *
     * @param putRequest the put request
     * @return response entity
     */
    @PostMapping("/users")
    public ResponseEntity<Object> addUser(@RequestBody @NonNull final UserCreateRequest putRequest) {

        userService.save(buildUserSaveDTO(putRequest));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User created successfully");
    }

    /**
     * method to build put object from request
     *
     * @param request create user request object
     * @return built UserSaveDTO from request object
     */
    private @NonNull UserSaveDTO buildUserSaveDTO(@NonNull final UserCreateRequest request) {

        final UserSaveDTO.UserSaveDTOBuilder builder = UserSaveDTO.builder()
                .userId(UniqueIdGenerator.generateUUID(USER_ID_PREFIX));

        if (!ValidatorUtil.isValidEmailId(request.getEmailId())) {
            throw new InvalidRequestException("Invalid input field : emailId");
        }

        return builder.emailId(request.getEmailId().trim())
                .name(request.getName())
                .build();
    }

}
