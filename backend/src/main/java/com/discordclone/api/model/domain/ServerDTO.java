package com.discordclone.api.model.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public record ServerDTO(
        @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
        UUID id,
        String name,
        String imageUrl,
        UUID inviteCode,
        UUID profileId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {

}
