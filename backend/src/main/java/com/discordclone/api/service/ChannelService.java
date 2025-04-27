package com.discordclone.api.service;

import com.discordclone.api.entity.Channel;
import com.discordclone.api.model.CreateChannelDto;
import com.discordclone.api.model.domain.ServerWithMembersAndChannelsDTO;

import java.util.UUID;

public interface ChannelService {
    ServerWithMembersAndChannelsDTO createChannel(CreateChannelDto createChannelDto, UUID serverId, UUID profileId);
    void saveChannel(Channel channel);
}
