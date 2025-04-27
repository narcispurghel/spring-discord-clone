package com.discordclone.api.exception;

import com.discordclone.api.model.response.ErrorResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            value = {
                    ApiException.class,
            })
    public ResponseEntity<ErrorResponseDTO> handleApiExceptions(ApiException e) {
        ErrorResponseDTO response =
                new ErrorResponseDTO(
                        e.getMessage(),
                        e.getHttpStatusCode().value(),
                        e.getDescription(),
                        e.getPath()
                );

        return new ResponseEntity<>(response, e.getHttpStatusCode());
    }
}
