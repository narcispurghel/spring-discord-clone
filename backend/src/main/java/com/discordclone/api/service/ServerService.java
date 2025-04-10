package com.discordclone.api.service;

import com.discordclone.api.dto.ChannelDto;
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
    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;

    public ServerService(
            ServerRepository serverRepository,
            JwtService jwtService,
            ProfileService profileService,
            ChannelRepository channelRepository,
            MemberService memberService,
            MemberRepository memberRepository, ProfileRepository profileRepository
    ) {
        this.serverRepository = serverRepository;
        this.jwtService = jwtService;
        this.profileService = profileService;
        this.channelRepository = channelRepository;
        this.memberService = memberService;
        this.memberRepository = memberRepository;
        this.profileRepository = profileRepository;
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

            HashSet<Server> servers = new HashSet<>();
            servers.add(savedServer);

            channelRepository.save(savedChannel);
            memberRepository.save(savedMember);

            ServerDto serverDTO = ServerMapper.toServerDTO(savedServer, currentUser.get().getId());

            return new ResponseEntity<>(serverDTO, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    // Get server by ID
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

    public boolean deleteServer(UUID id) {
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
