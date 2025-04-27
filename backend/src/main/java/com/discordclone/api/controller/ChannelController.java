package com.discordclone.api.controller;

import com.discordclone.api.model.CreateChannelDto;
import com.discordclone.api.model.domain.ServerWithMembersAndChannelsDTO;
import com.discordclone.api.service.ChannelService;
import com.discordclone.api.service.ProfileService;
import com.discordclone.api.service.impl.ChannelServiceImpl;
import com.discordclone.api.service.impl.ProfileServiceImpl;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/channels")
public class ChannelController {
    private final ChannelService channelService;
    private final ProfileService profileService;

    public ChannelController(
            ChannelServiceImpl channelService, ProfileServiceImpl profileServiceImpl) {
        this.channelService = channelService;
        this.profileService = profileServiceImpl;
    }

    @PostMapping()
    public ResponseEntity<ServerWithMembersAndChannelsDTO> addNewChannel(
            @RequestBody(required = false) CreateChannelDto newChannel,
            @RequestParam(name = "serverId", required = false) UUID serverId,
            Authentication authentication) {

        UUID profileId = profileService.getProfileIdFromAuth(authentication);
        ServerWithMembersAndChannelsDTO serverWithMembersAndChannelsDto = channelService.createChannel(newChannel, serverId, profileId);

        return ResponseEntity.status(HttpStatus.CREATED).body(serverWithMembersAndChannelsDto);
    }
}
