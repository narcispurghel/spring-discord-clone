package com.discordclone.controller;

import com.discordclone.model.Server;
import com.discordclone.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class ServerController {

    @Autowired
    private ServerService serverService;

    @PostMapping("/servers")
    public ResponseEntity<Server> createServer(@RequestBody Server server) {
        Server savedServer = serverService.createServer(server);
        return new ResponseEntity<>(savedServer, HttpStatus.CREATED);
    }
}
