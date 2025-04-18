package com.discordclone.api.service.impl;

import com.discordclone.api.model.CreateServerDto;
import com.discordclone.api.exception.impl.InvalidInputException;
import com.discordclone.api.entity.Channel;
import com.discordclone.api.entity.Member;
import com.discordclone.api.entity.Profile;
import com.discordclone.api.entity.Server;
import com.discordclone.api.repository.ChannelRepository;
import com.discordclone.api.repository.MemberRepository;
import com.discordclone.api.repository.ProfileRepository;
import com.discordclone.api.repository.ServerRepository;
import com.discordclone.api.service.ServerService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ServerServiceImpl implements ServerService {
    private final ServerRepository serverRepository;
    private final ProfileServiceImpl profileServiceImpl;
    private final ChannelRepository channelRepository;
    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;

    public ServerServiceImpl(
            ServerRepository serverRepository,
            ProfileServiceImpl profileServiceImpl,
            ChannelRepository channelRepository,
            MemberRepository memberRepository, ProfileRepository profileRepository
    ) {
        this.serverRepository = serverRepository;
        this.profileServiceImpl = profileServiceImpl;
        this.channelRepository = channelRepository;
        this.memberRepository = memberRepository;
        this.profileRepository = profileRepository;
    }

    @Override
    @Transactional
    public Server createServer(CreateServerDto data, UUID profileId) {
        Profile currentUser = profileServiceImpl.getProfileById(profileId);
        boolean serverExists = serverRepository.existsByName(data.getServerName());

        if (serverExists) {
            throw new InvalidInputException("Invalid serverName", data.getServerName() + "is used", HttpStatus.CONFLICT, "serverName");
        }

        Channel channel = new Channel();
        Member member = new Member();

        HashSet<Channel> channels = new HashSet<>();
        channels.add(channel);
        HashSet<Member> members = new HashSet<>();
        members.add(member);

        Server newServer = new Server()
                .setImageUrl(data.getServerImage())
                .setName(data.getServerName())
                .setInviteCode(UUID.randomUUID())
                .setChannels(channels)
                .setMembers(members);

        Channel savedChannel = channelRepository.save(channel);
        Member savedMember = memberRepository.save(member);

        Server savedServer = serverRepository.save(newServer);

        channelRepository.save(savedChannel);
        memberRepository.save(savedMember);

        Set<Server> servers = currentUser.getServers();
        servers.add(newServer);

        currentUser.setServers(servers);

        return savedServer;
    }

    @Override
    @Transactional
    public Server getServerById(UUID id) {
        if (id == null) {
            throw new InvalidInputException("Invalid server.id", "server id must be a non-null value", HttpStatus.BAD_REQUEST, "server.id");
        }

        Optional<Server> server = serverRepository.findById(id);

        return server.orElseThrow(() -> new InvalidInputException(
                "Invalid server.id",
                "Cannot find a server associated with id " + id,
                HttpStatus.BAD_REQUEST, "server.id")
        );
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
    public Set<Server> getAllServersByProfileId(UUID profileId) {
        Profile profile = profileRepository.findProfileById(profileId)
                .orElseThrow(() -> new InvalidInputException(
                        "Invalid profile.id",
                        "Cannot find a server associated with id " + profileId,
                        HttpStatus.BAD_REQUEST,
                        "profile.id")
                );

        return profile.getServers();
    }

}
