package com.discordclone.api.service.impl;

import com.discordclone.api.entity.Channel;
import com.discordclone.api.entity.Member;
import com.discordclone.api.entity.Profile;
import com.discordclone.api.entity.Server;
import com.discordclone.api.exception.impl.InvalidInputException;
import com.discordclone.api.model.CreateServerDTO;
import com.discordclone.api.model.domain.ServerDTO;
import com.discordclone.api.model.domain.ServerWithMembersAndChannelsDTO;
import com.discordclone.api.repository.MemberRepository;
import com.discordclone.api.repository.ProfileRepository;
import com.discordclone.api.repository.ServerRepository;
import com.discordclone.api.service.ServerService;
import com.discordclone.api.util.ChannelType;
import com.discordclone.api.util.ModelConverter;
import com.discordclone.api.util.Role;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServerServiceImpl implements ServerService {

    private final Logger logger = LoggerFactory.getLogger(ServerServiceImpl.class);
    private final ServerRepository serverRepository;
    private final ProfileRepository profileRepository;

    private final MemberRepository memberRepository;

    public ServerServiceImpl(
            ServerRepository serverRepository,
            ProfileRepository profileRepository,
            MemberRepository memberRepository) {
        this.serverRepository = serverRepository;
        this.profileRepository = profileRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public ServerDTO createServer(CreateServerDTO data, UUID profileId) {

        Profile currentUser = profileRepository.getProfileById(profileId)
                .orElseThrow(() -> new UsernameNotFoundException("Cannot find the profile with id " + profileId));

        boolean serverExists = serverRepository.existsByName(data.serverName());
        if (serverExists) {
            throw new InvalidInputException(
                    "Invalid serverName",
                    data.serverName() + "is used",
                    HttpStatus.CONFLICT, "serverName"
            );
        }

        Server newServer = new Server(data.serverName());
        newServer.setImageUrl(data.serverImage());
        newServer.getChannels().add(new Channel("General", ChannelType.TEXT, newServer));
        newServer.getMembers().add(new Member(Role.OWNER, currentUser, newServer));
        newServer = serverRepository.save(newServer);

        logger.debug("Server created {}", newServer);

        return ModelConverter.convertToServerDTO(newServer);
    }

    @Override
    @Transactional
    public ServerWithMembersAndChannelsDTO getServerById(UUID id) {

        if (id == null) {
            throw new InvalidInputException(
                    "Invalid server.id",
                    "server id must be a non-null value",
                    HttpStatus.BAD_REQUEST,
                    "server.id");
        }

        Server server = serverRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Cannot find the server with id " + id));

        return ModelConverter.convertToServerWithMembersAndChannelsDTO(server);
    }

    @Override
    @Transactional
    public boolean deleteServerById(UUID id) {
        Optional<Server> server = serverRepository.findById(id);

        if (server.isPresent()) {
            serverRepository.deleteById(id);
            return true;
        }

        return false;
    }

    @Override
    @Transactional
    public List<ServerDTO> getAllByMemberId(UUID profileId) {
        List<Member> members = memberRepository.getMemberByProfile_Id(profileId)
                .orElseThrow(() -> new UsernameNotFoundException("Cannot find the members with id " + profileId));

        return members.stream()
                .map(Member::getServer)
                .map(ModelConverter::convertToServerDTO)
                .toList();
    }
}
