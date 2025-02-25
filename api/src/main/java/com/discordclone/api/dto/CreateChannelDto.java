package com.discordclone.api.dto;

import com.discordclone.api.util.ChannelType;

public record CreateChannelDto(String channelName, ChannelType channelType) {
}
