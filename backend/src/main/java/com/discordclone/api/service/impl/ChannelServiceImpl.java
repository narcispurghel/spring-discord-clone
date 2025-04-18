package com.discordclone.api.service.impl;

import com.discordclone.api.model.CreateChannelDto;
import com.discordclone.api.model.ServerDto;
import com.discordclone.api.exception.impl.InvalidInputException;
import com.discordclone.api.entity.Channel;
import com.discordclone.api.entity.Server;
import com.discordclone.api.repository.ChannelRepository;
import com.discordclone.api.service.ChannelService;
import com.discordclone.api.util.mapper.ServerMapper;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class ChannelServiceImpl implements ChannelService {
    private final ChannelRepository channelRepository;
    private final ServerServiceImpl serverService;

    public ChannelServiceImpl(ChannelRepository channelRepository,
                              ServerServiceImpl serverService) {
        this.channelRepository = channelRepository;
        this.serverService = serverService;
    }

    public ServerDto createChannel(CreateChannelDto createChannelDto, UUID serverId, UUID profileId) {
        validateCreateChannelDto(createChannelDto);

        Server server = serverService.getServerById(serverId);

        Channel channel = new Channel()
                .setChannelName(createChannelDto.channelName())
                .setType(createChannelDto.channelType());

        Channel savedChannel = channelRepository.save(channel);

        Set<Channel> channels = server.getChannels();
        channels.add(savedChannel);

        server.setChannels(channels);

        return ServerMapper.toServerDTO(serverService.getServerById(serverId), profileId);
    }

    private void validateCreateChannelDto(CreateChannelDto data) {
        if (data == null ) {
            throw new InvalidInputException("Invalid channel", "channel must be a non-null value", HttpStatus.UNPROCESSABLE_ENTITY, "channel");
        }
        if (data.channelName() == null) {
            throw new InvalidInputException("Invalid channel.name", "name must be a non-null value", HttpStatus.BAD_REQUEST, "channel.name");
        }
        if (data.channelType() == null) {
            throw new InvalidInputException("Invalid channel.type", "name must be a non-null value", HttpStatus.BAD_REQUEST, "channel.type");
        }

    }
}
