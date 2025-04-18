package com.discordclone.api.model;

import com.discordclone.api.util.ChannelType;

import java.time.LocalDateTime;
import java.util.UUID;

public record ChannelDto(
        UUID id,
        String name,
        ChannelType type,
        UUID serverId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
