package org.crowdfund.controller;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.crowdfund.models.ProjectStatus;
import org.crowdfund.models.UserRole;
import org.crowdfund.models.db.User;
import org.crowdfund.pojo.ProjectDTO;
import org.crowdfund.pojo.UserDTO;
import org.crowdfund.pojo.UserCreateRequest;
import org.crowdfund.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.Instant;
import java.util.List;

/**
 * Controller class for User
 */
@RestController
@EnableWebMvc
@Slf4j
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    public UserController(@NonNull final UserService userService) {this.userService = userService;}

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String userId) {
        return ResponseEntity.ok().body(userService.getUserById(userId));
    }

    @GetMapping("/user/{userId}/project")
    public ResponseEntity<List<ProjectDTO>> getProjectsByStatus(
            @PathVariable String userId,
            @RequestParam("status") @NonNull final String status,
            @RequestParam("limit") @NonNull final Integer limit,
            @RequestParam("next") final String next
    ) {
        return ResponseEntity.ok(userService.getProjectsByStatus(userId,
                ProjectStatus.valueOf(status),
                limit,next));
    }

    /**
     * method to get User by UserRole
     *
     * @param role  user role
     * @param limit no of records per page
     * @param next  next sort key for fetching next set of records
     * @return list of users
     */
    @GetMapping("/user")
    public ResponseEntity<List<User>> getUserByRole(
            @RequestParam("role") final String role,
            @RequestParam("limit") @NonNull final Integer limit,
            @RequestParam("next") final String next) {

        return ResponseEntity.ok(userService.getUserByRole(role,
                limit, next));
    }


    /**
     * method to add new user
     *
     * @param putRequest
     * @return
     */
    @PostMapping("/user")
    public ResponseEntity<Object> addUser(@RequestBody @NonNull final UserCreateRequest putRequest) {

        userService.save(buildUserDTO(putRequest));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User created successfully");
    }

    /**
     * method to build put object from request
     *
     * @param putRequest
     * @return
     */
    private @NonNull UserDTO buildUserDTO(@NonNull final UserCreateRequest putRequest) {
        //TODO add more fields
        return UserDTO.builder()
                .userId(putRequest.getUserId())
                .name(putRequest.getName())
                .role(UserRole.valueOf(putRequest.getRole()))
                .build();
    }

}
