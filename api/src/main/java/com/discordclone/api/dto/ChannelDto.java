package com.discordclone.api.dto;

import com.discordclone.api.util.ChannelType;

import java.util.Date;
import java.util.UUID;

public record ChannelDto(
        UUID id,
        String name,
        ChannelType type,
        UUID serverId,
        Date createdAt,
        Date updatedAt
) {}
