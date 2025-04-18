package com.discordclone.api.service;

import com.discordclone.api.model.CreateChannelDto;
import com.discordclone.api.model.ServerDto;

import java.util.UUID;

public interface ChannelService {
    ServerDto createChannel(CreateChannelDto createChannelDto, UUID serverId, UUID profileId);
}
