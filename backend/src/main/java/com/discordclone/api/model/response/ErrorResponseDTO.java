package com.discordclone.api.model.response;

import java.time.LocalDateTime;

public record ErrorResponseDTO(
        String message, int statusCode, String description, String root, LocalDateTime timestamp) {

    public ErrorResponseDTO(String message, int statusCode, String description, String root) {
        this(message, statusCode, description, root, LocalDateTime.now());
    }
}
