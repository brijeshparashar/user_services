package com.demo.user.exception;

import com.demo.user.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  Exception handling Controller for different types of Exception
 */
@ControllerAdvice
@Slf4j
public class UserExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String RECORD_NOT_FOUND = "ER001";
    private static final String INVALID_INPUT_PAYLOAD = "ER002";
    private static final String ACCESS_DENIED = "ER003";

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseBody
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        log.error("ConstraintViolationException occurred {}", ex);
        ErrorResponse error = ErrorResponse.builder()
                .errorCode(INVALID_INPUT_PAYLOAD)
                .errorMessage(ex.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ResponseEntity<Object> onAccessDeniedException(final Exception ex) {
        log.error("AccessDenied Exception occurred:  {}", ex);
        ErrorResponse error = ErrorResponse.builder()
                .errorCode(ACCESS_DENIED)
                .errorMessage(HttpStatus.FORBIDDEN.toString()+":"+ex.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(UserInputException.class)
    @ResponseBody
    public ResponseEntity<Object> handleUserInputException(UserInputException ex, WebRequest request) {
        log.error("UserInputException occurred: {}", ex);
        ErrorResponse error = ErrorResponse.builder()
                .errorCode(INVALID_INPUT_PAYLOAD)
                .errorMessage(ex.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    @ResponseBody
    protected ResponseEntity<Object> handleRecordNotFound(RuntimeException ex, WebRequest request) {
        log.error("UserNot found exception occurred : {}", ex);
        ErrorResponse error = ErrorResponse.builder()
                .errorCode(RECORD_NOT_FOUND)
                .errorMessage(ex.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("MethodArgumentNotValidException occurred : {}", ex.getMessage());
        List<String> errorList = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> String.format("[%1$s [%2$s] %3$s]",
                        fieldError.getField(),
                        fieldError.getRejectedValue(),
                        fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        ErrorResponse error = ErrorResponse.builder()
                .errorCode(INVALID_INPUT_PAYLOAD)
                .errorMessage(ex.getMessage())
                .errors(errorList)
                .build();
        return handleExceptionInternal(ex, error, headers, HttpStatus.BAD_REQUEST, request);
    }
}
