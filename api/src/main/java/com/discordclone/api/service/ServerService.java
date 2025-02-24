package com.discordclone.api.service;

import com.discordclone.api.dto.CreateServerDto;
import com.discordclone.api.dto.ErrorResponseDto;
import com.discordclone.api.dto.ServerDTO;
import com.discordclone.api.model.Channel;
import com.discordclone.api.model.Member;
import com.discordclone.api.model.Profile;
import com.discordclone.api.model.Server;
import com.discordclone.api.repository.ChannelRepository;
import com.discordclone.api.repository.MemberRepository;
import com.discordclone.api.repository.ServerRepository;
import com.discordclone.api.security.JwtService;
import com.discordclone.api.util.mapper.ServerMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class ServerService {
    private final ServerRepository serverRepository;
    private final JwtService jwtService;
    private final ProfileService profileService;
    private final ChannelRepository channelRepository;
    private final MemberService memberService;
    private final ServerMapper serverMapper;
    private final MemberRepository memberRepository;

    public ServerService(
            ServerRepository serverRepository,
            JwtService jwtService,
            ProfileService profileService,
            ChannelRepository channelRepository,
            MemberService memberService,
            ServerMapper serverMapper,
            MemberRepository memberRepository
    ) {
        this.serverRepository = serverRepository;
        this.jwtService = jwtService;
        this.profileService = profileService;
        this.channelRepository = channelRepository;
        this.memberService = memberService;
        this.serverMapper = serverMapper;
        this.memberRepository = memberRepository;
    }

    public ResponseEntity<?> createServer(CreateServerDto createServerDto,
                                          HttpServletRequest request,
                                          Authentication authentication) {
        Optional<Profile> currentUser = profileService.getUserByEmail(authentication.getName());

        if (serverRepository.findByName(createServerDto.getServerName()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ErrorResponseDto(
                            HttpStatus.CONFLICT,
                            "Server with name " + createServerDto.getServerName() + " already exists!"
                    )
            );
        }

        if (currentUser.isPresent()) {
            Channel channel = new Channel();
            Member member = new Member();
            member.setProfile(currentUser.get());

            HashSet<Channel> channels = new HashSet<>();
            channels.add(channel);
            HashSet<Member> members = new HashSet<>();
            members.add(member);

            Server newServer = new Server()
                    .setImageUrl(createServerDto.getServerImage())
                    .setName(createServerDto.getServerName())
                    .setInviteCode(UUID.randomUUID())
                    .setProfile(currentUser.get())
                    .setChannels(channels)
                    .setMembers(members);

            Channel savedChannel = channelRepository.save(channel);
            Member savedMember = memberRepository.save(member);

            Server savedServer = serverRepository.save(newServer);

            HashSet<Server> servers = new HashSet<>();
            servers.add(savedServer);

            savedChannel.setServer(savedServer);
            savedMember.setServers(servers);

            channelRepository.save(savedChannel);
            memberRepository.save(savedMember);

            ServerDTO serverDTO = serverMapper.toServerDTO(savedServer);

            return new ResponseEntity<>(serverDTO, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    // Get server by ID
    public Optional<Server> getServerById(UUID id) { return serverRepository.findById(id); }

    public Optional<Server> updateServer(UUID id, Server updatedServer) {
        Optional<Server> existingServer = serverRepository.findById(id);

        if (existingServer.isPresent()) {
            Server server = existingServer.get();
            server.setName(updatedServer.getName());
            server.setImageUrl(updatedServer.getImageUrl());
            server.setChannels(server.getChannels());
            server.setMembers(server.getMembers());
            return Optional.of(serverRepository.save(server));
        }

        return Optional.empty();
    }

    public boolean deleteServer(UUID id) {
        Optional<Server> server = serverRepository.findById(id);

        if(server.isPresent()) {
            serverRepository.deleteById(id);
            return  true;
        }

        return false;
    }

    public Set<ServerDTO> getAllServersByProfileId(UUID profileId) {
        Set<Server> servers = serverRepository.findAllAsSetByProfileId(profileId);

        if (servers.isEmpty()) {
            return new HashSet<>();
        }

        return servers.stream().map(serverMapper::toServerDTO).collect(Collectors.toSet());
    }
}
