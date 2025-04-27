package com.discordclone.api.model.domain;

import com.discordclone.api.util.ChannelType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public record ChannelDTO(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        UUID id,
        String name,
        ChannelType type,
        UUID serverId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
