package com.discordclone.api.util.mapper;

import com.discordclone.api.dto.ServerDto;
import com.discordclone.api.model.Server;
import com.discordclone.api.repository.ProfileRepository;

import java.util.stream.Collectors;

public final class ServerMapper {
    private static ProfileRepository profileRepository;

    public ServerMapper(ProfileRepository profileRepository) {
        ServerMapper.profileRepository = profileRepository;
    }

    public static ServerDto toServerDTO(Server server) {
        return new ServerDto()
                .setId(server.getId())
                .setChannels(server.getChannels().stream().map(ChannelMapper::toDto).collect(Collectors.toSet()))
                .setMembers(server.getMembers().stream().map(MemberMapper::toDto).collect(Collectors.toSet()))
                .setName(server.getName())
                .setProfileId(server.getProfile().getId())
                .setUpdatedAt(server.getUpdatedAt())
                .setCreatedAt(server.getCreatedAt())
                .setInviteCode(server.getInviteCode())
                .setImageUrl(server.getImageUrl());
    }

    public static Server fromDTO(ServerDto serverDTO) {
        return new Server()
                .setId(serverDTO.getId())
                .setChannels(serverDTO.getChannels().stream().map(ChannelMapper::fromDto).collect(Collectors.toSet()))
                .setMembers(serverDTO.getMembers().stream().map(MemberMapper::fromDto).collect(Collectors.toSet()))
                .setName(serverDTO.getName())
                .setProfile(profileRepository.findById(serverDTO.getProfileId()).orElseThrow())
                .setUpdatedAt(serverDTO.getUpdatedAt())
                .setCreatedAt(serverDTO.getCreatedAt())
                .setInviteCode(serverDTO.getInviteCode())
                .setImageUrl(serverDTO.getImageUrl());
    }
}
