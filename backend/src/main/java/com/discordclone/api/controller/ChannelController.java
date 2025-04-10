package com.discordclone.api.controller;

import com.discordclone.api.dto.CreateChannelDto;
import com.discordclone.api.dto.ServerDto;
import com.discordclone.api.exception.InvalidInputException;
import com.discordclone.api.model.Profile;
import com.discordclone.api.repository.ProfileRepository;
import com.discordclone.api.service.ChannelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/channels")
public class ChannelController {
    private final ChannelService channelService;
    private final ProfileRepository profileRepository;

    public ChannelController(ChannelService channelService, ProfileRepository profileRepository) {
        this.channelService = channelService;
        this.profileRepository = profileRepository;
    }

    @PostMapping()
    public ResponseEntity<ServerDto> addNewChannel(
            @RequestBody(required = false) CreateChannelDto newChannel,
            @RequestParam(name = "serverId", required = false) UUID serverId,
            Authentication authentication) {
            Optional<Profile> profile = profileRepository.getProfileByEmail(authentication.getName());

            if (profile.isPresent()) {
                ServerDto serverDto = channelService.createChannel(newChannel, serverId, profile.get().getId());

                return ResponseEntity.status(HttpStatus.CREATED).body(serverDto);
            } else {
                throw new InvalidInputException("Profile not found");
            }
    }
}
