package com.demo.user.dao.repository;

import com.demo.user.dao.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository class to perform Crud operations on UserDetails table.
 */
@Repository
public interface UserRepository extends CrudRepository<UserEntity,Long>{
}
