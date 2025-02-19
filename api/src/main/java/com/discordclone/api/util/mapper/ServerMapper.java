package com.discordclone.api.util.mapper;

import com.discordclone.api.dto.ServerDTO;
import com.discordclone.api.entity.Server;
import com.discordclone.api.repository.ProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class ServerMapper {
    private final ProfileRepository profileRepository;

    public ServerMapper(
            ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public ServerDTO toServerDTO(Server server) {
        return new ServerDTO()
                .setId(server.getId())
                .setChannels(server.getChannels())
                .setMembers(server.getMembers())
                .setName(server.getName())
                .setProfileId(server.getProfile().getId())
                .setUpdatedAt(server.getUpdatedAt())
                .setCreatedAt(server.getCreatedAt())
                .setInviteCode(server.getInviteCode())
                .setImageUrl(server.getImageUrl());
    }

    @Transactional
    public Server fromDTO(ServerDTO serverDTO) {
        return new Server()
                .setId(serverDTO.getId())
                .setChannels(serverDTO.getChannels())
                .setMembers(serverDTO.getMembers())
                .setName(serverDTO.getName())
                .setProfile(profileRepository.findById(serverDTO.getProfileId()).get())
                .setUpdatedAt(serverDTO.getUpdatedAt())
                .setCreatedAt(serverDTO.getCreatedAt())
                .setInviteCode(serverDTO.getInviteCode())
                .setImageUrl(serverDTO.getImageUrl());
    }
}
