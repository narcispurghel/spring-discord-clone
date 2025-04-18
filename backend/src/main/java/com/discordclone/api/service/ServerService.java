package com.discordclone.api.service;

import com.discordclone.api.model.CreateServerDto;
import com.discordclone.api.entity.Server;

import java.util.Set;
import java.util.UUID;

public interface ServerService {
    Server createServer(CreateServerDto createServerDto, UUID profileId);
    Server getServerById(UUID id);
    boolean deleteServerById(UUID id);
    Set<Server> getAllServersByProfileId(UUID profileId);
}
