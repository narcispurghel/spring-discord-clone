package com.discordclone.api.model.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ServerWithMembersAndChannelsDTO(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        UUID id,
        String name,
        String imageUrl,
        UUID inviteCode,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        UUID profileId,
        List<MemberDTO> members,
        List<ChannelDTO> channels) {
}
