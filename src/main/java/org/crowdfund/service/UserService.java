package org.crowdfund.service;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.crowdfund.dao.UserDao;
import org.crowdfund.models.ProjectStatus;
import org.crowdfund.models.UserRole;
import org.crowdfund.models.db.User;
import org.crowdfund.pojo.ProjectDTO;
import org.crowdfund.pojo.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * service class to manage user ops
 */
@Log4j2
@Service
public class UserService {

    private final UserDao userDao;

    private final ProjectService projectService;

    public UserService(@NonNull final UserDao userDao,
                       @NonNull final ProjectService projectService) {
        this.userDao = userDao;
        this.projectService = projectService;
    }

    /**
     * @param userDTO
     */
    public void save(@NonNull final UserDTO userDTO) {
        userDao.save(userDTO);
    }

    /**
     * @param userId
     * @return
     */
    public UserDTO getUserById(@NonNull final String userId) {
        log.info("Request for fetching user details for user id {}", userId);
        return userDao.getUserById(userId);
    }

    /**
     * method to fetch users by user role
     *
     * @param userRole
     * @param limit    page size
     * @param next     next record key
     * @return list of users
     */
    public List<User> getUserByRole(@NonNull final String userRole,
                                    @NonNull final Integer limit,
                                    final String next) {
        return userDao.getUserByRole(UserRole.valueOf(userRole), limit, next);
    }

    public List<ProjectDTO> getProjectsByStatus(@NonNull final String userId,
                                                @NonNull final ProjectStatus projectStatus,
                                                @NonNull Integer limit, String next) {
        return projectService.getProjectsByInnovatorIdAndStatus(userId,
                projectStatus, limit, next);

    }
}
