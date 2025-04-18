package com.discordclone.api.exception;

import com.discordclone.api.exception.impl.InvalidInputException;
import com.discordclone.api.model.response.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {
            ApiException.class,
    })
    public ResponseEntity<ErrorResponseDto> handleApiExceptions(ApiException e) {
        ErrorResponseDto response = new ErrorResponseDto(
                e.getMessage(),
                e.getHttpStatusCode().toString(),
                e.getDescription(),
                e.getPath()
        );

        return new ResponseEntity<>(response, e.getHttpStatusCode());
    }
}