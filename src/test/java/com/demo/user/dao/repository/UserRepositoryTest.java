package com.demo.user.dao.repository;

import com.demo.user.dao.entity.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Test class to validate Repository methods by loading the actual DB objects.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenGetById_thenReturnUser() {
        Long userId = Long.valueOf(1000002);
        Optional<UserEntity> user = userRepository.findById(userId);
        assertNotNull(user.get());
        assertEquals(user.get().getFirstName(), "David");
    }

    @Test
    public void whenGetById_WithNoRecordInDB_thenReturnOptionalEmpty() {
        Long userId = Long.valueOf(1000009);
        Optional<UserEntity> user = userRepository.findById(userId);
        assertFalse(user.isPresent());
    }

    @Test
    public void whenUpdateUser_thenReturnUpdatedUser() {
        UserEntity user = userRepository.save(mockUserEntity());
        assertNotNull(user);
        assertEquals(user.getFirstName(), "firstName");
    }

    private UserEntity mockUserEntity() {
        return UserEntity.builder()
                .userId(1000001)
                .firstName("firstName")
                .lastName("lastname")
                .gender("male")
                .title("Mr")
                .build();
    }

}