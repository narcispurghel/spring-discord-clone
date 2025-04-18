package com.discordclone.api.model.response;

import java.time.LocalDateTime;

public record ErrorResponseDto (
        String message,
        String statusCode,
        String description,
        String root,
        LocalDateTime timestamp) {

    public ErrorResponseDto(String message, String statusCode, String description, String root) {
        this(message, statusCode, description, root, LocalDateTime.now());
    }
}