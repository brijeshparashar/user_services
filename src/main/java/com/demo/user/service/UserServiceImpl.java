package com.demo.user.service;

import com.demo.user.exception.UserInputException;
import com.demo.user.exception.UserNotFoundException;
import com.demo.user.dao.entity.UserEntity;
import com.demo.user.dao.repository.UserDAO;
import com.demo.user.mapper.UserMapper;
import com.demo.user.model.UserAddress;
import com.demo.user.model.UserDetails;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Service class provides business logic implementation for different user details Api operations.
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDAO userDAO;

    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);


    /**
     * Retrieves  user from database using userId.
     * Transforms DB entities to model objects.
     * Throws <code>{@link UserNotFoundException}</code> exception if user is not found in database.
     * @return
     */
    @Override
    public UserDetails getUser(Long userId) {
        return userDAO.getUserByIdWithCircuitBreaker(userId)
                .map(userEntity -> UserDetails.builder()
                        .userId(userEntity.getUserId())
                        .title(userEntity.getTitle())
                        .firstName(userEntity.getFirstName())
                        .lastName(userEntity.getLastName())
                        .gender(userEntity.getGender())
                        .address(UserAddress.builder()
                                .street(userEntity.getAddress().getStreet())
                                .city(userEntity.getAddress().getCity())
                                .state(userEntity.getAddress().getState())
                                .postcode(userEntity.getAddress().getPostcode())
                                .build())
                        .build())
                .orElseThrow(()-> new UserNotFoundException("User not found."));
    }

    /**
     * Method saves new user information to database.
     * Transforms DB entities to model objects.
     * Throws <code>{@link UserNotFoundException}</code> exception if user is not found in database.
     * @return
     */
    @Override
    @Transactional
    public UserDetails updateUser(UserDetails userDetails) {
        if (userDetails == null) {
            throw new UserInputException("User Input Payload is null.");
        }
        UserEntity userEntity = userDAO.getUserById(userDetails.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found."));
        // update existing user entity with new user details
        userMapper.mapUserDetailsToUserEntity(userDetails,userEntity);
        //save data to db
        UserEntity updateUserEntity = userDAO.updateUser(userEntity);
        //transform entity to domain object.
        UserDetails.UserDetailsBuilder userBuilder = UserDetails.builder().userId(updateUserEntity.getUserId())
                .title(updateUserEntity.getTitle())
                .firstName(updateUserEntity.getFirstName())
                .lastName(updateUserEntity.getLastName())
                .gender(updateUserEntity.getGender());

        if (null != updateUserEntity.getAddress()) {
            userBuilder
                    .address(UserAddress.builder()
                            .street(updateUserEntity.getAddress().getStreet())
                            .city(updateUserEntity.getAddress().getCity())
                            .state(updateUserEntity.getAddress().getState())
                            .postcode(updateUserEntity.getAddress().getPostcode())
                            .build());

        }
        return userBuilder.build();
    }
}
