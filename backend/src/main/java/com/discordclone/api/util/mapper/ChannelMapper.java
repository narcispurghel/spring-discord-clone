package com.discordclone.api.util.mapper;

import com.discordclone.api.dto.ChannelDto;
import com.discordclone.api.model.Channel;
import com.discordclone.api.repository.ServerRepository;

public final class ChannelMapper {
    private static ServerRepository serverRepository;

    public ChannelMapper(ServerRepository serverRepository) {
        ChannelMapper.serverRepository = serverRepository;
    }

    public static ChannelDto toDto(Channel channel) {
        return new ChannelDto(
                channel.getId(),
                channel.getChannelName(),
                channel.getType(),
                channel.getServer().getId(),
                channel.getCreatedAt
                        (),
                channel.getUpdatedAt()
        );
    }

    public static Channel fromDto(ChannelDto channelDto) {
        return new Channel()
                .setChannelName(channelDto.name())
                .setId(channelDto.id())
                .setCreatedAt(channelDto.createdAt())
                .setUpdatedAt(channelDto.updatedAt())
                .setType(channelDto.type())
                .setServer(serverRepository.findById(channelDto.serverId()).orElseThrow());
    }
}
