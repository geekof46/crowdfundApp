package org.crowdfund.service;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.crowdfund.dao.UserDao;
import org.crowdfund.exceptions.InvalidRequestException;
import org.crowdfund.exceptions.UserNotFoundException;
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

    /**
     * Instantiates a new User service.
     *
     * @param userDao the user dao
     */
    public UserService(@NonNull final UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Save.
     *
     * @param userSaveDTO the user save dto
     */
    public void save(@NonNull final UserSaveDTO userSaveDTO) {
        try{
            userDao.getUserById(userSaveDTO.getEmailId());
            throw new InvalidRequestException("User already exist with emailId "+userSaveDTO.getEmailId());
        }catch(final UserNotFoundException e){
            userDao.save(userSaveDTO);
        }

    }

    /**
     * Gets user by id.
     *
     * @param userId the user id
     * @return user by id
     */
    public UserDTO getUserById(@NonNull final String userId) {
        log.info("Request for fetching user details for user id {}", userId);
        return userDao.getUserById(userId);
    }
}
