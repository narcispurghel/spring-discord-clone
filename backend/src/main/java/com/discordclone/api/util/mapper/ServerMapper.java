package com.discordclone.api.util.mapper;

import com.discordclone.api.dto.ServerDto;
import com.discordclone.api.model.Server;
import com.discordclone.api.repository.ProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ServerMapper {
    private final ProfileRepository profileRepository;
    private final ChannelMapper channelMapper;
    private final MemberMapper memberMapper;

    public ServerMapper(
            ProfileRepository profileRepository, ChannelMapper channelMapper, MemberMapper memberMapper) {
        this.profileRepository = profileRepository;
        this.channelMapper = channelMapper;
        this.memberMapper = memberMapper;
    }

    public ServerDto toServerDTO(Server server) {
        return new ServerDto()
                .setId(server.getId())
                .setChannels(server.getChannels().stream().map(channelMapper::toDto).collect(Collectors.toSet()))
                .setMembers(server.getMembers().stream().map(memberMapper::toDto).collect(Collectors.toSet()))
                .setName(server.getName())
                .setProfileId(server.getProfile().getId())
                .setUpdatedAt(server.getUpdatedAt())
                .setCreatedAt(server.getCreatedAt())
                .setInviteCode(server.getInviteCode())
                .setImageUrl(server.getImageUrl());
    }

    @Transactional
    public Server fromDTO(ServerDto serverDTO) {
        return new Server()
                .setId(serverDTO.getId())
                .setChannels(serverDTO.getChannels().stream().map(channelMapper::fromDto).collect(Collectors.toSet()))
                .setMembers(serverDTO.getMembers().stream().map(memberMapper::fromDto).collect(Collectors.toSet()))
                .setName(serverDTO.getName())
                .setProfile(profileRepository.findById(serverDTO.getProfileId()).orElseThrow())
                .setUpdatedAt(serverDTO.getUpdatedAt())
                .setCreatedAt(serverDTO.getCreatedAt())
                .setInviteCode(serverDTO.getInviteCode())
                .setImageUrl(serverDTO.getImageUrl());
    }
}
