package com.demo.user.exception;

/**
 * Exception class  to handle User NOT found exception.
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
