package com.demo.user.dao.repository;

import com.demo.user.dao.entity.UserEntity;

import java.sql.SQLException;
import java.util.Optional;

public interface UserDAO {
    Optional<UserEntity> getUserByIdWithCircuitBreaker(Long userId);
    Optional<UserEntity> getUserById(Long userId);
    UserEntity updateUser(UserEntity userEntity);
}
