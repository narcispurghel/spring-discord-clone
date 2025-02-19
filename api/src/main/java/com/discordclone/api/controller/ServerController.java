package com.discordclone.api.controller;

import com.discordclone.api.dto.CreateServerDto;
import com.discordclone.api.dto.ServerDTO;
import com.discordclone.api.entity.Server;
import com.discordclone.api.repository.ServerRepository;
import com.discordclone.api.service.ServerService;
import com.discordclone.api.util.mapper.ServerMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@RestController
@Transactional
@RequestMapping("/api")
public class    ServerController {

    private final ServerService serverService;
    private final ServerRepository serverRepository;
    private final ServerMapper serverMapper;

    public ServerController(
            ServerService serverService,
            ServerRepository serverRepository, ServerMapper serverMapper
    ) {
        this.serverService = serverService;
        this.serverRepository = serverRepository;
        this.serverMapper = serverMapper;
    }

    @GetMapping("server")
    public ResponseEntity<Collection<Server>> getServers() {
        return ResponseEntity.status(HttpStatus.OK).body(serverService.getAllServers());
    }

    @PostMapping("/servers")
    public ResponseEntity<?> createServer(@RequestBody CreateServerDto createServerDto,
                                          HttpServletRequest request) {
        return serverService.createServer(createServerDto, request);
    }

    @GetMapping("/servers/{id}/server-with-channels-members-and-profiles")
    public ResponseEntity<?> getServerById(@PathVariable("id") UUID id) {
        Optional<Server> server = serverRepository.findById(id);

        if(server.isPresent()) {
            ServerDTO response = serverMapper.toServerDTO(server.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Server " + id + " not found!", HttpStatus.NOT_FOUND);
        }
    }

}
