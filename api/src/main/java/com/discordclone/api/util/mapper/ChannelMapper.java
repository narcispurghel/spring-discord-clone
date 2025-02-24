package com.discordclone.api.util.mapper;

import com.discordclone.api.dto.ChannelDto;
import com.discordclone.api.model.Channel;
import com.discordclone.api.repository.ServerRepository;
import org.springframework.stereotype.Component;

@Component
public class ChannelMapper {
    private final ServerRepository serverRepository;

    public ChannelMapper(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    public ChannelDto toDto(Channel channel) {
        return new ChannelDto(
                channel.getId(),
                channel.getChannelName(),
                channel.getType(),
                channel.getServer().getId(),
                channel.getCreatedAt(),
                channel.getUpdatedAt()
        );
    }

    public Channel fromDto(ChannelDto channelDto) {
        return new Channel()
                .setChannelName(channelDto.name())
                .setId(channelDto.id())
                .setCreatedAt(channelDto.createdAt())
                .setUpdatedAt(channelDto.updatedAt())
                .setType(channelDto.type())
                .setServer(serverRepository.findById(channelDto.serverId()).orElseThrow());
    }
}
