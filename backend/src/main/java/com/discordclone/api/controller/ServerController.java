package com.discordclone.api.controller;

import com.discordclone.api.dto.CreateServerDto;
import com.discordclone.api.dto.ProfileDto;
import com.discordclone.api.dto.ServerDto;
import com.discordclone.api.exception.InvalidInputException;
import com.discordclone.api.model.Profile;
import com.discordclone.api.model.Server;
import com.discordclone.api.repository.ProfileRepository;
import com.discordclone.api.repository.ServerRepository;
import com.discordclone.api.service.ProfileService;
import com.discordclone.api.service.ServerService;
import com.discordclone.api.util.mapper.ProfileMapper;
import com.discordclone.api.util.mapper.ServerMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@Transactional
@RequestMapping("/api")
//TODO create specific responses types for every request
public class    ServerController {
    private final ServerService serverService;
    private final ServerRepository serverRepository;
    private final ProfileRepository profileRepository;
    private final ProfileService profileService;

    public ServerController(
            ServerService serverService,
            ServerRepository serverRepository,
            ProfileRepository profileRepository,
            ProfileService profileService) {
        this.serverService = serverService;
        this.serverRepository = serverRepository;
        this.profileRepository = profileRepository;
        this.profileService = profileService;
    }

    @GetMapping("/servers")
    public ResponseEntity<?> getServers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Optional<ProfileDto> currentUser = profileRepository.findByEmail(authentication.getName()).map(ProfileMapper::toProfileDTO);

        if (currentUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(serverService.getAllServersByProfileId(currentUser.get().getId()));
        }

        throw new UsernameNotFoundException("User does not exist");
    }

    @PostMapping("/servers")
    //TODO replace anonymous returned type with a specific one
    public ResponseEntity<?> createServer(@RequestBody CreateServerDto createServerDto,
                                          HttpServletRequest request,
                                          Authentication authentication) {
        return serverService.createServer(createServerDto, request, authentication);
    }

    @GetMapping("/servers/{id}/server-with-channels-members-and-profiles")
    public ResponseEntity<ServerDto> getServerById(@PathVariable("id") UUID id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UUID profileId = profileService.getProfileIdFromAuth(authentication);
        ServerDto response = ServerMapper.toServerDTO(serverService.getServerById(id), profileId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
