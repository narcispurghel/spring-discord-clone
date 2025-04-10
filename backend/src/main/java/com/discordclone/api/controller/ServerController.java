package com.discordclone.api.controller;

import com.discordclone.api.dto.CreateServerDto;
import com.discordclone.api.dto.ServerDto;
import com.discordclone.api.service.ProfileService;
import com.discordclone.api.service.ServerService;
import com.discordclone.api.util.mapper.ServerMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Transactional
@RequestMapping("/api")
//TODO create specific responses types for every request
public class    ServerController {
    private final ServerService serverService;
    private final ProfileService profileService;

    public ServerController(ServerService serverService,
                            ProfileService profileService) {
        this.serverService = serverService;
        this.profileService = profileService;
    }

    @GetMapping("/servers")
    public ResponseEntity<?> getServers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UUID profileId = profileService.getProfileIdFromAuth(authentication);
        return ResponseEntity.status(HttpStatus.OK).body(serverService.getAllServersByProfileId(profileId));
    }

    @PostMapping("/servers")
    //TODO replace anonymous returned type with a specific one
    public ResponseEntity<?> createServer(@RequestBody CreateServerDto createServerDto, Authentication authentication) {
        return serverService.createServer(createServerDto, authentication);
    }

    @GetMapping("/servers/{id}/server-with-channels-members-and-profiles")
    public ResponseEntity<ServerDto> getServerById(@PathVariable("id") UUID id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UUID profileId = profileService.getProfileIdFromAuth(authentication);
        ServerDto response = ServerMapper.toServerDTO(serverService.getServerById(id), profileId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
