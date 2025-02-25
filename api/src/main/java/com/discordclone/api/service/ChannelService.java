package com.discordclone.api.service;

import com.discordclone.api.dto.ChannelDto;
import com.discordclone.api.dto.CreateChannelDto;
import com.discordclone.api.dto.ServerDto;
import com.discordclone.api.exception.InvalidInputException;
import com.discordclone.api.exception.RequestBodyNullException;
import com.discordclone.api.model.Channel;
import com.discordclone.api.model.Server;
import com.discordclone.api.repository.ChannelRepository;
import com.discordclone.api.util.mapper.ChannelMapper;
import com.discordclone.api.util.mapper.ServerMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class ChannelService {
    private final ChannelRepository channelRepository;
    private final ServerService serverService;
    private final ServerMapper serverMapper;
    private final ChannelMapper channelMapper;

    public ChannelService(ChannelRepository channelRepository,
                          ServerService serverService,
                          ServerMapper serverMapper,
                          ChannelMapper channelMapper) {
        this.channelRepository = channelRepository;
        this.serverService = serverService;
        this.serverMapper = serverMapper;
        this.channelMapper = channelMapper;
    }

    public ServerDto createChannel(CreateChannelDto createChannelDto, UUID serverId) {
        validateCreateChannelDto(createChannelDto);

        Server server = serverService.getServerById(serverId);

        Channel channel = new Channel()
                .setChannelName(createChannelDto.channelName())
                .setType(createChannelDto.channelType())
                .setServer(server);

        Channel savedChannel = channelRepository.save(channel);

        return serverMapper.toServerDTO(serverService.getServerById(serverId));
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
