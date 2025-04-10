package com.discordclone.api.util.mapper;

import com.discordclone.api.dto.ServerDto;
import com.discordclone.api.model.Server;

import java.util.UUID;
import java.util.stream.Collectors;

public final class ServerMapper {

    public static ServerDto toServerDTO(Server server, UUID profileId) {
        if (server == null) return null;

        return new ServerDto(
                server.getId(),
                server.getName(),
                server.getImageUrl(),
                server.getInviteCode(),
                profileId,
                server.getCreatedAt(),
                server.getUpdatedAt(),
                server.getChannels().stream().map(channel -> ChannelMapper.toDto(channel, server.getId())).collect(Collectors.toSet()),
                server.getMembers().stream().map(member -> MemberMapper.toDto(member, profileId, server.getId())).collect(Collectors.toSet()));
    }

    public static Server fromDTO(ServerDto serverDTO) {
        if (serverDTO == null) return null;

        return new Server()
                .setId(serverDTO.getId())
                .setChannels(serverDTO.getChannels().stream().map(ChannelMapper::fromDto).collect(Collectors.toSet()))
                .setMembers(serverDTO.getMembers().stream().map(MemberMapper::fromDto).collect(Collectors.toSet()))
                .setName(serverDTO.getName())
                .setUpdatedAt(serverDTO.getUpdatedAt())
                .setCreatedAt(serverDTO.getCreatedAt())
                .setInviteCode(serverDTO.getInviteCode())
                .setImageUrl(serverDTO.getImageUrl());
    }
}
