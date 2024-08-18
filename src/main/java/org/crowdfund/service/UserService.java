package org.crowdfund.service;

import lombok.NonNull;
import org.crowdfund.dao.UserDao;
import org.crowdfund.models.UserDTO;

/**
 * service class to manage user ops
 */
public class UserService {

    private final UserDao userDao;

    public UserService(@NonNull final UserDao userDao) {this.userDao = userDao;}

    /**
     *
     * @param userDTO
     */
    public void save(@NonNull final UserDTO userDTO) {
        userDao.save(userDTO);
    }

    /**
     *
     * @param userId
     * @return
     */
    public UserDTO getUserById(@NonNull final String userId) {
        return userDao.getUserById(userId);
    }
}
