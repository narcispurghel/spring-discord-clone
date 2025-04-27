package com.discordclone.api.service.impl;

import com.discordclone.api.entity.Channel;
import com.discordclone.api.entity.Member;
import com.discordclone.api.entity.Server;
import com.discordclone.api.exception.impl.InvalidInputException;
import com.discordclone.api.exception.impl.UnprocessableEntityException;
import com.discordclone.api.model.CreateChannelDto;
import com.discordclone.api.model.domain.ServerWithMembersAndChannelsDTO;
import com.discordclone.api.repository.ChannelRepository;
import com.discordclone.api.repository.ServerRepository;
import com.discordclone.api.service.ChannelService;
import com.discordclone.api.util.ModelConverter;
import com.discordclone.api.util.Role;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ChannelServiceImpl implements ChannelService {
    private final ChannelRepository channelRepository;
    private final ServerRepository serverRepository;

    public ChannelServiceImpl(
            ChannelRepository channelRepository,
            ServerRepository serverRepository) {
        this.channelRepository = channelRepository;
        this.serverRepository = serverRepository;
    }

    @Transactional
    @Override
    public ServerWithMembersAndChannelsDTO createChannel(
            CreateChannelDto createChannelDto,
            UUID serverId,
            UUID profileId) {

        validateCreateChannelDto(createChannelDto);

        Server server = serverRepository.findById(serverId)
                .orElseThrow(() -> new IllegalArgumentException("sv not found"));

        List<Member> members = server.getMembers();
        if (members == null) {
            throw new IllegalArgumentException("sv members is null");
        }

        boolean isMember = members.stream()
                .anyMatch(member -> member.getProfile().getId().equals(profileId));

        if (!isMember) {
            throw new IllegalArgumentException("Current user is not member");
        }

        boolean isAdminOrOwner = members.stream()
                .anyMatch(member -> member.getRole().equals(Role.ADMIN)
                        || member.getRole().equals(Role.OWNER));

        if (!isAdminOrOwner) {
            throw new IllegalArgumentException("Current user is not owner");
        }

        server.getChannels().add(new Channel(
                createChannelDto.channelName(),
                createChannelDto.channelType(),
                server)
        );

        return ModelConverter.convertToServerWithMembersAndChannelsDTO(serverRepository.save(server));
    }

    @Override
    public void saveChannel(Channel channel) {
        if (channel == null) {
            throw new UnprocessableEntityException(
                    "Invalid channel",
                    "channel must be a non-null value",
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "channel");
        }

        channelRepository.save(channel);
    }

    private void validateCreateChannelDto(CreateChannelDto data) {
        if (data == null) {
            throw new InvalidInputException(
                    "Invalid channel",
                    "channel must be a non-null value",
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "channel");
        }
        if (data.channelName() == null) {
            throw new InvalidInputException(
                    "Invalid channel.name",
                    "name must be a non-null value",
                    HttpStatus.BAD_REQUEST,
                    "channel.name");
        }
        if (data.channelType() == null) {
            throw new InvalidInputException(
                    "Invalid channel.type",
                    "name must be a non-null value",
                    HttpStatus.BAD_REQUEST,
                    "channel.type");
        }
    }
}
