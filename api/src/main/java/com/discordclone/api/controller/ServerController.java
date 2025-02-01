package com.discordclone.api.controller;

import com.discordclone.api.dto.CreateServerDto;
import com.discordclone.api.model.Server;
import com.discordclone.api.repository.ServerRepository;
import com.discordclone.api.service.ServerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class    ServerController {

    private final ServerService serverService;
    private final ServerRepository serverRepository;

    public ServerController(
            ServerService serverService,
            ServerRepository serverRepository
    ) {
        this.serverService = serverService;
        this.serverRepository = serverRepository;
    }

    @PostMapping("/servers")
    public ResponseEntity<?> createServer(@RequestBody CreateServerDto createServerDto, HttpServletRequest request) {
        try {
            return serverService.createServer(createServerDto, request);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @Transactional
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
