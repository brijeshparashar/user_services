package com.demo.user.service;

import com.demo.user.exception.UserInputException;
import com.demo.user.exception.UserNotFoundException;
import com.demo.user.model.UserAddress;
import com.demo.user.model.UserDetails;
import com.demo.user.dao.entity.AddressEntity;
import com.demo.user.dao.entity.UserEntity;
import com.demo.user.dao.repository.UserDAO;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class UserServiceImplTest {

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserServiceImpl userService;


    @Test
    public void whenGetUser_AndUserPresentInDb_ThenUserReturnedSuccessfully() {
        Long userId = Long.valueOf("1000001");
        Mockito.when(userDAO.getUserByIdWithCircuitBreaker(userId))
                .thenReturn(Optional.of(mockUserEntity(userId)));
        UserDetails userDetails = userService.getUser(userId);
        Assert.assertNotNull(userDetails);
        Assert.assertEquals(userDetails.getFirstName(), "firstName");
    }

    @Test
    public void whenGetUser_AndUserNotInDb_ThenRecordNotFoundException() {
        Long userId = Long.valueOf("1000001");
        Mockito.when(userDAO.getUserByIdWithCircuitBreaker(userId))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getUser(userId));
    }

    @Test
    public void whenUpdateUser_AndInputPayloadIsNull_ThenUserInputException() {

        Assertions.assertThrows(UserInputException.class, () -> userService.updateUser(null));
    }

    @Test
    public void whenUpdateUser_AndUserNotPresentInDb_ThenRecordNotFoundException() {
        Mockito.when(userDAO.getUserById(Long.valueOf("1000001")))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.updateUser(mockUserDetails(Long.valueOf(1000001))));
    }


    @Test
    public void whenUpdateUser_AndUserPresentInDb_ThenUpdateIsSuccessful() {
        Long userId = Long.valueOf("1000001");

        UserEntity savedUserEntity = mockUserEntity(userId);
        UserEntity newUserEntity = UserEntity.builder()
                .firstName("updatedFirstName")
                .build();
        Mockito.when(userDAO.getUserById(userId))
                .thenReturn(Optional.of(savedUserEntity));
        Mockito.when(userDAO.updateUser(Mockito.any(UserEntity.class)))
                .thenReturn(newUserEntity);
        UserDetails userDetails = userService.updateUser(UserDetails.builder()
                .userId(userId)
                .firstName("updatedFirstName")
                .build());
        Assertions.assertNotNull(userDetails);
        Assertions.assertEquals(userDetails.getFirstName(), "updatedFirstName");

    }

    private UserDetails mockUserDetails(Long userId) {
        return UserDetails.builder()
                .firstName("firstName")
                .lastName("lastname")
                .gender("male")
                .title("Mr")
                .userId(userId)
                .address(mockAddress())
                .build();
    }

    private UserAddress mockAddress() {
        return UserAddress.builder()
                .city("Melbourne")
                .postcode("3000")
                .state("VIC")
                .street("Healy street")
                .build();
    }

    private UserEntity mockUserEntity(Long userId) {
        Long addressId = Long.valueOf("1022");
        return UserEntity.builder()
                .firstName("firstName")
                .lastName("lastname")
                .gender("male")
                .title("Mr")
                .userId(userId)
                .address(mockAddressEntity(addressId))
                .build();
    }

    private AddressEntity mockAddressEntity(Long addressId) {
        return AddressEntity.builder()
                .addressId(addressId)
                .city("Melbourne")
                .postcode("3000")
                .state("VIC")
                .street("Healy street")
                .build();
    }
}