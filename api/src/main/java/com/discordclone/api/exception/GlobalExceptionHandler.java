package com.discordclone.api.exception;

import com.discordclone.api.dto.ErrorResponseDto;
import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResponseDto> handleIOException(IOException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponseDto(HttpStatus.BAD_REQUEST, e.getMessage())
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> handleServletException(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponseDto(HttpStatus.BAD_REQUEST, e.getMessage())
        );
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidInputException(InvalidInputException e) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                new ErrorResponseDto(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage())
        );
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<ErrorResponseDto> handleServisssceException(UnsupportedOperationException e) {
        e.printStackTrace();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ErrorResponseDto(HttpStatus.UNAUTHORIZED, e.getMessage())
        );
    }

    @ExceptionHandler(RequestBodyNullException.class)
    public ResponseEntity<ErrorResponseDto> handleRequestBodyNullException(RequestBodyNullException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponseDto(HttpStatus.BAD_REQUEST, e.getMessage())
        );
    }

    @ExceptionHandler(MissingOrMalformedJwtException.class)
    public ResponseEntity<ErrorResponseDto> handleMissingOrMalformedJwtException(MissingOrMalformedJwtException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ErrorResponseDto(HttpStatus.UNAUTHORIZED, e.getMessage())
        );
    }

    @ExceptionHandler(PropertyValueException.class)
    public ResponseEntity<String> handlePropertyValueException(PropertyValueException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorResponseDto(HttpStatus.NOT_FOUND, e.getMessage())
        );
    }


    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvocationTargetException.class)
    public ResponseEntity<ErrorResponseDto> handleInvocationTargetException(InvocationTargetException e) {
        return ResponseEntity.status(401).body(
                new ErrorResponseDto(
                        HttpStatus.valueOf(401),
                        e.getMessage()));
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponseDto> handleAuthorizationDeniedException(AuthorizationDeniedException e) {
        return ResponseEntity.status(401).body(
                new ErrorResponseDto(
                        HttpStatus.valueOf(401),
                        e.getMessage()));
    }
}