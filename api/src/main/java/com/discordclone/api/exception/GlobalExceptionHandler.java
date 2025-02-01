package com.discordclone.api.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException e) {
        // Log the exception (you can use a logger here)
        return new ResponseEntity<>("I/O Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ServletException.class)
    public ResponseEntity<String> handleServletException(ServletException e) {
        // Log the exception (you can use a logger here)
        return new ResponseEntity<>("Servlet Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MissingOrMalformedJwtException.class)
    public ResponseEntity<String> handleMissingOrMalformedJwtException(MissingOrMalformedJwtException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request){
        return ResponseEntity.status(401).body(new ErrorResponse() {
            @Override
            @NonNull
            public HttpStatusCode getStatusCode() {
                return HttpStatus.UNAUTHORIZED;
            }

            @Override
            @NonNull
            public ProblemDetail getBody() {
                return ProblemDetail.forStatus(401);
            }
        });
    }

    @ExceptionHandler
    public ResponseEntity<MissingOrMalformedJwtException> handleAuthorizationDeniedException(AuthorizationServiceException e) {
        return new ResponseEntity<>
                (new MissingOrMalformedJwtException("Authorization header is missing"), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<String> handlePropertyValueException(PropertyValueException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
