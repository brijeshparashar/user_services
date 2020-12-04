package com.demo.user.dao.repository;

import com.demo.user.dao.entity.AddressEntity;
import com.demo.user.dao.entity.UserEntity;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repository layer abstracting Spring Data JPA CRUD functions with custom implementations.
 */
@Repository
public class UserDAOImpl implements UserDAO {

    private static final List<UserEntity> userEntities = getUserEntities();

    @Autowired
    private UserRepository userRepository;


    /**
     * Method returns  user present in UserDetail table of db using userId.
     * If DB operation is failed, method triggers a fallback mechanism using hystrix
     * which could then returns records from cache. For the sake of this application the
     * records are returned from a predefined and prepopulated object.
     *
     * @return
     */
    @HystrixCommand(
            fallbackMethod = "getUserByIdFallback")
    @Override
    public Optional<UserEntity> getUserByIdWithCircuitBreaker(Long userId) {
        /*The following commented code is to simulate the error scenario so that the fallback
         * flow could be tested.
         * To test the Circuit Breaker's fallback flow uncomment the following code and restart.  */
        /*        String a = null;
            a.toString();*/
        return userRepository.findById(userId);
    }

    /**
     * Method returns  user present in UserDetail table of db using userId without any fallbacks.
     *
     * @return
     */
    @Override
    public Optional<UserEntity> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    /**
     * Method to update UserDetails into Database.
     *
     * @param userEntity {@link UserEntity}
     * @return {@link UserEntity}
     */
    @Override
    public UserEntity updateUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }


    /**
     * @param userId {@link String}
     * @return {@link Optional<UserEntity>}.
     */
    public Optional<UserEntity> getUserByIdFallback(Long userId) {
        return userEntities
                .stream()
                .filter(usrEntity -> usrEntity.getUserId() == userId)
                .findAny();
    }

    private static List<UserEntity> getUserEntities() {
        List<UserEntity> userEntities = new ArrayList<>();
        UserEntity userEntity = UserEntity.builder()
                .userId(Long.valueOf("1000001"))
                .firstName("James")
                .lastName("Scott")
                .gender("male")
                .title("Mr")
                .address(AddressEntity.builder()
                        .addressId(Long.valueOf("1008"))
                        .city("Gold Coast")
                        .postcode("2000")
                        .state("QLD")
                        .street("Sun Street")
                        .build())
                .build();
        userEntities.add(userEntity);
        return userEntities;
    }
}
