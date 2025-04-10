package com.discordclone.api.controller;

import com.discordclone.api.dto.CreateChannelDto;
import com.discordclone.api.dto.ServerDto;
import com.discordclone.api.repository.ProfileRepository;
import com.discordclone.api.service.ChannelService;
import com.discordclone.api.service.ProfileService;
import com.discordclone.api.service.impl.ChannelServiceImpl;
import com.discordclone.api.service.impl.ProfileServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/channels")
public class ChannelController {
    private final ChannelService channelService;
    private final ProfileService profileService;

    public ChannelController(ChannelServiceImpl channelService,
                             ProfileServiceImpl profileServiceImpl) {
        this.channelService = channelService;
        this.profileService = profileServiceImpl;
    }

    @PostMapping()
    public ResponseEntity<ServerDto> addNewChannel(
            @RequestBody(required = false) CreateChannelDto newChannel,
            @RequestParam(name = "serverId", required = false) UUID serverId,
            Authentication authentication) {

            UUID profileId = profileService.getProfileIdFromAuth(authentication);
            ServerDto serverDto = channelService.createChannel(newChannel, serverId, profileId);

            return ResponseEntity.status(HttpStatus.CREATED).body(serverDto);
    }
}
