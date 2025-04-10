package com.discordclone.api.exception;

import com.discordclone.api.dto.ErrorResponseDto;
import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {IOException.class, BadCredentialsException.class, RequestBodyNullException.class})
    public ResponseEntity<ErrorResponseDto> handleBadRequestExceptions(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponseDto(HttpStatus.BAD_REQUEST, e.getMessage())
        );
    }

    @ExceptionHandler(value = {InvalidInputException.class, PropertyValueException.class})
    public ResponseEntity<ErrorResponseDto> handleUnprocessableEntityExceptions(Exception e) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                new ErrorResponseDto(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage())
        );
    }

    @ExceptionHandler(value = {UnsupportedOperationException.class, MissingOrMalformedJwtException.class,
            InvocationTargetException.class, AuthorizationDeniedException.class})
    public ResponseEntity<ErrorResponseDto> handleUnauthorizedExceptions(Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ErrorResponseDto(HttpStatus.UNAUTHORIZED, e.getMessage())
        );
    }

    @ExceptionHandler(value = {UsernameNotFoundException.class, NoSuchElementException.class})
    public ResponseEntity<?> handleNotFoundExceptions(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorResponseDto(HttpStatus.NOT_FOUND, e.getMessage())
        );
    }

    private static final String DEFAULT_MISSING_BODY_MESSAGE = "Required request body is missing";

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        if (ex.getMessage() != null && ex.getMessage().contains(DEFAULT_MISSING_BODY_MESSAGE)) {
            throw new RequestBodyNullException();
        }

        throw ex;
    }
}