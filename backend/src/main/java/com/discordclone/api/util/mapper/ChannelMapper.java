package com.discordclone.api.util.mapper;

import com.discordclone.api.model.ChannelDto;
import com.discordclone.api.entity.Channel;

import java.util.UUID;

public final class ChannelMapper {

    public static ChannelDto toDto(Channel channel, UUID channelId) {
        if (channel == null) { return null; }

        return new ChannelDto(
                channel.getId(),
                channel.getChannelName(),
                channel.getType(),
                channelId,
                channel.getCreatedAt(),
                channel.getUpdatedAt()
        );
    }

    public static Channel fromDto(ChannelDto channelDto) {
        if (channelDto == null) { return null; }

        return new Channel()
                .setChannelName(channelDto.name())
                .setId(channelDto.id())
                .setCreatedAt(channelDto.createdAt())
                .setUpdatedAt(channelDto.updatedAt())
                .setType(channelDto.type());
    }
}
