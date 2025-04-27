package com.discordclone.api.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ProfileDTO(
        @JsonIgnore UUID id,
        String name,
        String email,
        List<MemberDTO> members,
        String imageUrl,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
