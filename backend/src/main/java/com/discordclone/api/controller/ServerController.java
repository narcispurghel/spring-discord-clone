package com.discordclone.api.controller;

import com.discordclone.api.entity.Server;
import com.discordclone.api.model.CreateServerDto;
import com.discordclone.api.model.ServerDto;
import com.discordclone.api.service.ProfileService;
import com.discordclone.api.service.ServerService;
import com.discordclone.api.util.mapper.ServerMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ServerController {
    private final ServerService serverService;
    private final ProfileService profileService;

    public ServerController(ServerService serverService,
                            ProfileService profileService) {
        this.serverService = serverService;
        this.profileService = profileService;
    }

    @GetMapping("/servers")
    public ResponseEntity<Set<ServerDto>> getServers(Authentication authentication) {
        UUID profileId = profileService.getProfileIdFromAuth(authentication);
        Set<Server> profileServers = serverService.getAllServersByProfileId(profileId);

        Set<ServerDto> response = profileServers.stream()
                .map(server -> ServerMapper.toServerDTO(server, profileId))
                .collect(Collectors.toSet());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/servers")
    public ResponseEntity<ServerDto> createServer(@RequestBody CreateServerDto createServerDto, Authentication authentication) {
        UUID profileId = profileService.getProfileIdFromAuth(authentication);
        Server createdServer = serverService.createServer(createServerDto, profileId);
        ServerDto serverDto = ServerMapper.toServerDTO(createdServer, profileId);

        return ResponseEntity.status(HttpStatus.CREATED).body(serverDto);
    }

    @GetMapping("/servers/{id}/server-with-channels-members-and-profiles")
    public ResponseEntity<ServerDto> getServerById(@PathVariable("id") UUID id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UUID profileId = profileService.getProfileIdFromAuth(authentication);
        ServerDto response = ServerMapper.toServerDTO(serverService.getServerById(id), profileId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
