package com.discordclone.api.model.domain;

import com.discordclone.api.util.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record MemberDTO(
        @JsonIgnore
        UUID id,
        Role role,
        UUID serverId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
