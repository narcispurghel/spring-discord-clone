package com.discordclone.api.controller;

import com.discordclone.api.dto.CreateChannelDto;
import com.discordclone.api.dto.ServerDto;
import com.discordclone.api.service.ChannelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/channels")
public class ChannelController {
    private final ChannelService channelService;

    public ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }

    @PostMapping()
    public ResponseEntity<ServerDto> addNewChannel(
            @RequestBody(required = false) CreateChannelDto newChannel,
            @RequestParam(name = "serverId", required = false) UUID serverId) {
        try {
            ServerDto serverDto = channelService.createChannel(newChannel, serverId);

            return ResponseEntity.status(HttpStatus.CREATED).body(serverDto);
        }
        catch (Exception e) {
            throw e;
        }
    }
}
