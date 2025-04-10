package com.discordclone.api.service;

import com.discordclone.api.dto.CreateChannelDto;
import com.discordclone.api.dto.ServerDto;

import java.util.UUID;

public interface ChannelService {
    ServerDto createChannel(CreateChannelDto createChannelDto, UUID serverId, UUID profileId);
}
