package com.demo.user.exception;

/**
 * Exception class to handle exceptions related to the Input Payload.
 */
public class UserInputException extends RuntimeException {

    public UserInputException(String message) {
        super(message);
    }
}
