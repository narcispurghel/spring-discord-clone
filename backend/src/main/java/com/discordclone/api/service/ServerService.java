package com.discordclone.api.service;

import com.discordclone.api.dto.CreateServerDto;
import com.discordclone.api.dto.ServerDto;
import com.discordclone.api.model.Server;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Set;
import java.util.UUID;

public interface ServerService {
    ResponseEntity<?> createServer(CreateServerDto createServerDto, Authentication authentication);
    Server getServerById(UUID id);
    boolean deleteServerById(UUID id);
    Set<ServerDto> getAllServersByProfileId(UUID profileId);
}
