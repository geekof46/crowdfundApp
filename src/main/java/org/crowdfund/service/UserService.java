package org.crowdfund.service;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.crowdfund.dao.UserDao;
import org.crowdfund.models.UserDTO;
import org.crowdfund.pojo.UserSaveDTO;
import org.springframework.stereotype.Service;

/**
 * service class to manage user ops
 */
@Log4j2
@Service
public class UserService {

    private final UserDao userDao;

    public UserService(@NonNull final UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * @param userSaveDTO
     */
    public void save(@NonNull final UserSaveDTO userSaveDTO) {
        //TODO add validation that no record exist for same emailId
        userDao.save(userSaveDTO);
    }

    /**
     * @param userId
     * @return
     */
    public UserDTO getUserById(@NonNull final String userId) {
        log.info("Request for fetching user details for user id {}", userId);
        return userDao.getUserById(userId);
    }
}
