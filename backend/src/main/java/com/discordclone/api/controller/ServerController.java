package com.discordclone.api.controller;

import com.discordclone.api.dto.CreateServerDto;
import com.discordclone.api.dto.ProfileDto;
import com.discordclone.api.dto.ServerDto;
import com.discordclone.api.repository.ProfileRepository;
import com.discordclone.api.repository.ServerRepository;
import com.discordclone.api.service.impl.ProfileServiceImpl;
import com.discordclone.api.service.impl.ServerServiceImpl;
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
    private final ServerServiceImpl serverServiceImpl;
    private final ServerRepository serverRepository;
    private final ProfileRepository profileRepository;
    private final ProfileServiceImpl profileServiceImpl;

    public ServerController(
            ServerServiceImpl serverServiceImpl,
            ServerRepository serverRepository,
            ProfileRepository profileRepository,
            ProfileServiceImpl profileServiceImpl) {
        this.serverServiceImpl = serverServiceImpl;
        this.serverRepository = serverRepository;
        this.profileRepository = profileRepository;
        this.profileServiceImpl = profileServiceImpl;
    }

    @GetMapping("/servers")
    public ResponseEntity<?> getServers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Optional<ProfileDto> currentUser = profileRepository.findByEmail(authentication.getName()).map(ProfileMapper::toProfileDTO);

        if (currentUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(serverServiceImpl.getAllServersByProfileId(currentUser.get().getId()));
        }

        throw new UsernameNotFoundException("User does not exist");
    }

    @PostMapping("/servers")
    //TODO replace anonymous returned type with a specific one
    public ResponseEntity<?> createServer(@RequestBody CreateServerDto createServerDto, Authentication authentication) {
        return serverServiceImpl.createServer(createServerDto, authentication);
    }

    @GetMapping("/servers/{id}/server-with-channels-members-and-profiles")
    public ResponseEntity<ServerDto> getServerById(@PathVariable("id") UUID id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UUID profileId = profileServiceImpl.getProfileIdFromAuth(authentication);
        ServerDto response = ServerMapper.toServerDTO(serverServiceImpl.getServerById(id), profileId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
