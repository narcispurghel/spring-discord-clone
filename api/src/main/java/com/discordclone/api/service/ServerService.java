package com.discordclone.api.service;

import com.discordclone.api.dto.CreateServerDto;
import com.discordclone.api.dto.ServerDTO;
import com.discordclone.api.entity.Channel;
import com.discordclone.api.entity.Member;
import com.discordclone.api.entity.Profile;
import com.discordclone.api.entity.Server;
import com.discordclone.api.repository.ChannelRepository;
import com.discordclone.api.repository.ServerRepository;
import com.discordclone.api.util.mapper.ServerMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Transactional
@Service
public class ServerService {
    private final ServerRepository serverRepository;
    private final JwtService jwtService;
    private final ProfileService profileService;
    private final ChannelRepository channelRepository;
    private final MemberService memberService;
    private final ServerMapper serverMapper;

    public ServerService(
            ServerRepository serverRepository,
            JwtService jwtService,
            ProfileService profileService,
            ChannelRepository channelRepository,
            MemberService memberService, ServerMapper serverMapper
    ) {
        this.serverRepository = serverRepository;
        this.jwtService = jwtService;
        this.profileService = profileService;
        this.channelRepository = channelRepository;
        this.memberService = memberService;
        this.serverMapper = serverMapper;
    }

    public ResponseEntity<?> createServer(CreateServerDto createServerDto,
                                          HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String username = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("Jwt")) {
                    System.out.println(cookie.getName());
                    username = jwtService.extractUsername(cookie.getValue());
                    break;
                }
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Optional<Profile> currentUser = profileService.getUserByEmail(username);

        if (currentUser.isPresent()) {
            Channel channel = new Channel();
            Member member = new Member();
            member.setProfile(currentUser.get());

            Server newServer = new Server()
                    .setImageUrl(createServerDto.getServerImage())
                    .setName(createServerDto.getServerName())
                    .setInviteCode(UUID.randomUUID())
                    .setProfile(currentUser.get())
                    .setChannels(Set.of(channel))
                    .setMembers(Set.of(member));

            channelRepository.save(channel);
            memberService.createMember(member);

            Server savedServer = serverRepository.save(newServer);
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

    public List<Server> getAllServers() {
        return serverRepository.findAll();
    }
}
