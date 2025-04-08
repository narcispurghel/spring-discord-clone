package com.discordclone.api.controller;

import com.discordclone.api.dto.CreateServerDto;
import com.discordclone.api.dto.ProfileDto;
import com.discordclone.api.dto.ServerDto;
import com.discordclone.api.model.Server;
import com.discordclone.api.repository.ProfileRepository;
import com.discordclone.api.repository.ServerRepository;
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
public class    ServerController {

    private final ServerService serverService;
    private final ServerRepository serverRepository;
    private final ServerMapper serverMapper;
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    public ServerController(
            ServerService serverService,
            ServerRepository serverRepository,
            ServerMapper serverMapper,
            ProfileRepository profileRepository,
            ProfileMapper profileMapper
    ) {
        this.serverService = serverService;
        this.serverRepository = serverRepository;
        this.serverMapper = serverMapper;
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
    }

    @GetMapping("/servers")
    public ResponseEntity<?> getServers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Optional<ProfileDto> currentUser = profileRepository.findByEmail(authentication.getName()).map(profileMapper::toProfileDTO);

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
    public ResponseEntity<?> getServerById(@PathVariable("id") UUID id) {
        Optional<Server> server = serverRepository.findById(id);

        if(server.isPresent()) {
            ServerDto response = serverMapper.toServerDTO(server.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Server " + id + " not found!", HttpStatus.NOT_FOUND);
        }
    }

}
