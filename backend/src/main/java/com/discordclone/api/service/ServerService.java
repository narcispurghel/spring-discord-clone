package com.discordclone.api.service;

import com.discordclone.api.model.CreateServerDTO;
import com.discordclone.api.model.domain.ServerDTO;
import com.discordclone.api.model.domain.ServerWithMembersAndChannelsDTO;

import java.util.List;
import java.util.UUID;

public interface ServerService {
    ServerDTO createServer(CreateServerDTO createServerDto, UUID profileId);

    ServerWithMembersAndChannelsDTO getServerById(UUID id);

    boolean deleteServerById(UUID id);

    List<ServerDTO> getAllByMemberId(UUID memberId);
}
