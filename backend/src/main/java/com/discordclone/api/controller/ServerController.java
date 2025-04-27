package com.discordclone.api.controller;

import com.discordclone.api.model.CreateServerDTO;
import com.discordclone.api.model.domain.ServerDTO;
import com.discordclone.api.model.domain.ServerWithMembersAndChannelsDTO;
import com.discordclone.api.service.ProfileService;
import com.discordclone.api.service.ServerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ServerController {

    private final ServerService serverService;
    private final ProfileService profileService;

    public ServerController(ServerService serverService, ProfileService profileService) {
        this.serverService = serverService;
        this.profileService = profileService;
    }

    @GetMapping("/servers")
    public ResponseEntity<List<ServerDTO>> getServersByProfile(Authentication authentication) {

        UUID profileId = profileService.getProfileIdFromAuth(authentication);
        List<ServerDTO> profileServers = serverService.getAllByMemberId(profileId);

        return new ResponseEntity<>(profileServers, HttpStatus.OK);
    }

    @PostMapping("/servers")
    public ResponseEntity<ServerDTO> createServer(
            @RequestBody CreateServerDTO createServerDto,
            Authentication authentication) {

        UUID profileId = profileService.getProfileIdFromAuth(authentication);
        ServerDTO createdServer = serverService.createServer(createServerDto, profileId);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdServer);
    }

    @GetMapping("/servers/{id}/server-with-channels-members-and-profiles")
    public ResponseEntity<ServerWithMembersAndChannelsDTO> getServerById(@PathVariable("id") UUID id) {

        ServerWithMembersAndChannelsDTO response = serverService.getServerById(id);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
