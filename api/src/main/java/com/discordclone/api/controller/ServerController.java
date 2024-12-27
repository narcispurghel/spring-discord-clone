package com.discordclone.api.controller;

import com.discordclone.api.dto.CreateServerDto;
import com.discordclone.api.model.Channel;
import com.discordclone.api.model.Member;
import com.discordclone.api.model.Profile;
import com.discordclone.api.model.Server;
import com.discordclone.api.repository.ChannelRepository;
import com.discordclone.api.repository.MemberRepository;
import com.discordclone.api.repository.ServerRepository;
import com.discordclone.api.service.JwtService;
import com.discordclone.api.service.ServerService;
import com.discordclone.api.service.UserService;
import com.discordclone.api.template.CreateServerResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ServerController {

    private static final Logger log = LoggerFactory.getLogger(ServerController.class);
    private final ServerService serverService;
    private final UserService userService;
    private final JwtService jwtService;
    private final ServerRepository serverRepository;
    private final ChannelRepository channelRepository;
    private final MemberRepository memberRepository;

    public ServerController(ServerService serverService, UserService userService, JwtService jwtService, ServerRepository serverRepository, ChannelRepository channelRepository, MemberRepository memberRepository) {
        this.serverService = serverService;
        this.userService = userService;
        this.jwtService = jwtService;
        this.serverRepository = serverRepository;
        this.channelRepository = channelRepository;
        this.memberRepository = memberRepository;
    }

    @PostMapping("/servers")
    public ResponseEntity<?> createServer(@RequestBody CreateServerDto createServerDto, HttpServletRequest request) {
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

        Optional<Profile> currentUser = userService.getUserByEmail(username);

        if (currentUser.isPresent()) {
            Server newServer = new Server()
                    .setImageUrl(createServerDto.getServerImage())
                    .setName(createServerDto.getServerName())
                    .setInviteCode(UUID.randomUUID())
                    .setProfileId(currentUser.get());

            Server savedServer = serverService.createServer(newServer);

            Channel channel = new Channel()
                    .setServerId(savedServer.getId())
                    .setServer(savedServer);

            Member member = new Member()
                    .setProfile(currentUser.get());

            Channel savedChannel = channelRepository.save(channel);
            Member savedMember = memberRepository.save(member);

            newServer.addChannel(savedChannel);
            newServer.addMember(savedMember);

            savedServer.addMember(savedMember);
            savedServer.addChannel(savedChannel);
            serverRepository.save(savedServer);

            CreateServerResponse response = new CreateServerResponse()
                    .setId(savedServer.getId())
                    .setName(savedServer.getName())
                    .setImageUrl(savedServer.getImageUrl())
                    .setUpdatedAt(savedServer.getUpdatedAt())
                    .setCreatedAt(savedServer.getCreatedAt())
                    .setInviteCode(savedServer.getInviteCode());

            return new ResponseEntity<>(savedServer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/servers/{id}/server-with-channels-members-and-profiles")
    public ResponseEntity<?> getServerById(@PathVariable("id") UUID id) {
        try {
            Optional<Server> server = serverRepository.findById(id);

            if(server.isPresent()) {
                return new ResponseEntity<Server>(server.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<String>(String.format("Server %s not found\n", id), HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
