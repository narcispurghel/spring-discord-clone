package com.discordclone.api.model;

import com.discordclone.api.util.ChannelType;

public record CreateChannelDto(String channelName, ChannelType channelType) {
}
