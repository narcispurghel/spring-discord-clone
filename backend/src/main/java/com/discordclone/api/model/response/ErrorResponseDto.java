package com.discordclone.api.model.response;

import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

public record ErrorResponseDto (
        String message,
        HttpStatusCode statusCode,
        String description,
        String root,
        LocalDateTime timestamp)
{

}