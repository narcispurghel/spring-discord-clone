package com.discordclone.api.dto;

import org.springframework.http.HttpStatusCode;

public record ErrorResponseDto (HttpStatusCode statusCode,
                                String message) { }