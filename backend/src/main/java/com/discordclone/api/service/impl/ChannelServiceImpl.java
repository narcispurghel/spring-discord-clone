package com.discordclone.api.service.impl;

import com.discordclone.api.dto.CreateChannelDto;
import com.discordclone.api.dto.ServerDto;
import com.discordclone.api.exception.InvalidInputException;
import com.discordclone.api.exception.RequestBodyNullException;
import com.discordclone.api.model.Channel;
import com.discordclone.api.model.Server;
import com.discordclone.api.repository.ChannelRepository;
import com.discordclone.api.service.ChannelService;
import com.discordclone.api.util.mapper.ServerMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class ChannelServiceImpl implements ChannelService {
    private final ChannelRepository channelRepository;
    private final ServerServiceImpl serverServiceImpl;

    public ChannelServiceImpl(ChannelRepository channelRepository,
                              ServerServiceImpl serverServiceImpl) {
        this.channelRepository = channelRepository;
        this.serverServiceImpl = serverServiceImpl;
    }

    public ServerDto createChannel(CreateChannelDto createChannelDto, UUID serverId, UUID profileId) {
        validateCreateChannelDto(createChannelDto);

        Server server = serverServiceImpl.getServerById(serverId);

        Channel channel = new Channel()
                .setChannelName(createChannelDto.channelName())
                .setType(createChannelDto.channelType());

        Channel savedChannel = channelRepository.save(channel);

        Set<Channel> channels = server.getChannels();
        channels.add(savedChannel);

        server.setChannels(channels);

        return ServerMapper.toServerDTO(serverServiceImpl.getServerById(serverId), profileId);
    }

    private void validateCreateChannelDto(CreateChannelDto channelDto) {
        if (channelDto == null ) {
            throw new RequestBodyNullException();
        }

        if (channelDto.channelName() == null) {
            throw new InvalidInputException("Channel name is required");
        }

        if (channelDto.channelType() == null) {
            throw new InvalidInputException("Channel type is required");
        }

    }
}
