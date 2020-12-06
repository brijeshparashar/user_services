package com.demo.user.controller;

import com.demo.user.UserApplication;
import com.demo.user.model.ErrorResponse;
import com.demo.user.model.UserAddress;
import com.demo.user.model.UserDetails;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = UserApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void whenGetUser_AndUserPresentInDb_passingValidUserInHeader_thenReturnUserJsonObject() throws Exception {
        HttpEntity request = new HttpEntity(getValidUserAuthorizationHeader());
        ResponseEntity<UserDetails> response = restTemplate.exchange(
                "http://localhost:" + port + "/users/1000002",
                HttpMethod.GET,
                request,
                UserDetails.class
        );
        assertEquals("David", response.getBody().getFirstName());
    }

    @Test
    public void whenGetUser_AndUserNOTPresentInDb_passingValidUserInHeader_thenReturnUserJsonObject() throws Exception {
        HttpEntity request = new HttpEntity(getValidUserAuthorizationHeader());
        ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                "http://localhost:" + port + "/users/1000008",
                HttpMethod.GET,
                request,
                ErrorResponse.class
        );
        assertEquals("ER001", response.getBody().getErrorCode());
    }

    @Test
    public void whenGetUser_AndUserPresentInDb_passingInvalidUserInHeader_thenReturnUserObject() throws Exception {
        HttpEntity request = new HttpEntity(getInvalidUserAuthorizationHeader());
        ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                "http://localhost:" + port + "/users/1000002",
                HttpMethod.GET,
                request,
                ErrorResponse.class
        );
        assertEquals("ER003", response.getBody().getErrorCode());
    }
    @Test
    public void whenUpdateUser_AndPassing_ADMIN_UserInHeader_thenUserShouldBeUpdated() throws Exception {
        HttpEntity<UserDetails> request = new HttpEntity<UserDetails>(mockUser(),getValidUserAuthorizationHeader());
        ResponseEntity<UserDetails> response = restTemplate.exchange(
                "http://localhost:" + port + "/users",
                HttpMethod.PUT,
                request,
                UserDetails.class
        );
        assertEquals("updatedFirstName", response.getBody().getFirstName());
    }

    @Test
    public void whenUpdateUser_AndPassing_ADMIN_UserInHeader_WithNULLPayload_thenReturnErrorCode() throws Exception {
        HttpEntity<UserDetails> request = new HttpEntity<UserDetails>(new UserDetails(),getValidUserAuthorizationHeader());
        ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                "http://localhost:" + port + "/users",
                HttpMethod.PUT,
                request,
                ErrorResponse.class
        );
        assertEquals("ER002", response.getBody().getErrorCode());
    }


    @Test
    public void whenUpdateUser_AndPassing_READ_UPDATE_UserInHeader_thenUserShouldBeUpdated() throws Exception {
        HttpEntity request = new HttpEntity(mockUser(),getReadUpdateUserAuthorizationHeader());
        ResponseEntity<UserDetails> response = restTemplate.exchange(
                "http://localhost:" + port + "/users",
                HttpMethod.PUT,
                request,
                UserDetails.class
        );
        assertEquals("updatedFirstName", response.getBody().getFirstName());
    }

    @Test
    public void whenUpdateUser_AndPassing_READ_ONLY_UserInHeader_thenReturnError() throws Exception {
        HttpEntity request = new HttpEntity(mockUser(),getReadOnlyUserAuthorizationHeader());
        ResponseEntity<ErrorResponse> response = restTemplate.exchange(
                "http://localhost:" + port + "/users",
                HttpMethod.PUT,
                request,
                ErrorResponse.class
        );
        assertEquals("ER003", response.getBody().getErrorCode());
    }


    private HttpHeaders getValidUserAuthorizationHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("1000001", "test123");
        return headers;
    }

    private HttpHeaders getAdminUserAuthorizationHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("1000005", "test123");
        return headers;
    }

    private HttpHeaders getReadUpdateUserAuthorizationHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("1000003", "test123");
        return headers;
    }

    private HttpHeaders getReadOnlyUserAuthorizationHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("1000004", "test123");
        return headers;
    }

    private HttpHeaders getInvalidUserAuthorizationHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("1000008", "test123");
        return headers;
    }

    private UserDetails mockUser() {
        return UserDetails.builder()
                .userId(Long.valueOf("1000003"))
                .firstName("updatedFirstName")
                .lastName("lastname")
                .gender("male")
                .title("Mr")
                .address(mockAddress())
                .build();
    }

    private UserAddress mockAddress() {
        return UserAddress.builder()
                .city("Sydney")
                .postcode("2000")
                .state("NSW")
                .street("Test street")
                .build();
    }

}