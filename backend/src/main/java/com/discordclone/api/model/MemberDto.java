package com.discordclone.api.model;

import com.discordclone.api.util.Role;

import java.util.Date;
import java.util.UUID;

public record MemberDto(
        UUID id,
        Role role,
        UUID profileId,
        UUID serverId,
        Date createdAt,
        Date updatedAt
) {
}
