package com.discordclone.api.service.impl;

import com.discordclone.api.dto.CreateServerDto;
import com.discordclone.api.dto.ErrorResponseDto;
import com.discordclone.api.dto.ServerDto;
import com.discordclone.api.exception.InvalidInputException;
import com.discordclone.api.model.Channel;
import com.discordclone.api.model.Member;
import com.discordclone.api.model.Profile;
import com.discordclone.api.model.Server;
import com.discordclone.api.repository.ChannelRepository;
import com.discordclone.api.repository.MemberRepository;
import com.discordclone.api.repository.ProfileRepository;
import com.discordclone.api.repository.ServerRepository;
import com.discordclone.api.security.JwtService;
import com.discordclone.api.service.ServerService;
import com.discordclone.api.util.mapper.ServerMapper;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class ServerServiceImpl implements ServerService {
    private final ServerRepository serverRepository;
    private final ProfileServiceImpl profileServiceImpl;
    private final ChannelRepository channelRepository;
    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;

    public ServerServiceImpl(
            ServerRepository serverRepository,
            JwtService jwtService,
            ProfileServiceImpl profileServiceImpl,
            ChannelRepository channelRepository,
            MemberServiceImpl memberServiceImpl,
            MemberRepository memberRepository, ProfileRepository profileRepository
    ) {
        this.serverRepository = serverRepository;
        this.profileServiceImpl = profileServiceImpl;
        this.channelRepository = channelRepository;
        this.memberRepository = memberRepository;
        this.profileRepository = profileRepository;
    }

    public ResponseEntity<?> createServer(CreateServerDto createServerDto, Authentication authentication) {
        Optional<Profile> currentUser = profileServiceImpl.getUserByEmail(authentication.getName());

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

            HashSet<Channel> channels = new HashSet<>();
            channels.add(channel);
            HashSet<Member> members = new HashSet<>();
            members.add(member);

            Server newServer = new Server()
                    .setImageUrl(createServerDto.getServerImage())
                    .setName(createServerDto.getServerName())
                    .setInviteCode(UUID.randomUUID())
                    .setChannels(channels)
                    .setMembers(members);

            Channel savedChannel = channelRepository.save(channel);
            Member savedMember = memberRepository.save(member);

            Server savedServer = serverRepository.save(newServer);

            channelRepository.save(savedChannel);
            memberRepository.save(savedMember);

            ServerDto serverDTO = ServerMapper.toServerDTO(savedServer, currentUser.get().getId());

            return new ResponseEntity<>(serverDTO, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    public Server getServerById(UUID id) {
        if (id == null) {
            throw new InvalidInputException("Valid is required");
        }

        Optional<Server> server = serverRepository.findById(id);

       if (server.isEmpty()) {
           //TODO: create custom exception
           throw new RuntimeException();
       }

        return server.get();
    }

    public boolean deleteServerById(UUID id) {
        Optional<Server> server = serverRepository.findById(id);

        if(server.isPresent()) {
            serverRepository.deleteById(id);
            return  true;
        }

        return false;
    }

    public Set<ServerDto> getAllServersByProfileId(UUID profileId) {
        Profile profile = profileRepository.findProfileById(profileId).orElseThrow(() -> new InvalidInputException("Invalid profile id"));

        Set<Server> servers = profile.getServers();

        return servers.stream().map(server -> ServerMapper.toServerDTO(server, profileId)).collect(Collectors.toSet());
    }

}
