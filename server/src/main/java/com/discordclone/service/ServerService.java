package com.discordclone.service;

import com.discordclone.model.Server;
import com.discordclone.repository.ServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServerService {
    @Autowired
    private ServerRepository serverRepository;

    // Create a new server
    public Server createServer(Server server) {
        return serverRepository.save(server);
    }

    // Get server by ID
    public Optional<Server> getServerById(Long id) { return serverRepository.findById(id); }

    public Optional<Server> updateServer(Long id, Server updatedServer) {
        Optional<Server> existingServer = serverRepository.findById(id);

        if (existingServer.isPresent()) {
            Server server = existingServer.get();
            server.setName(updatedServer.getName());
            server.setImage(updatedServer.getImage());
            return Optional.of(serverRepository.save(server));
        }

        return Optional.empty();
    }

    // Delete a server
    public boolean deleteServer(Long id) {
        Optional<Server> server = serverRepository.findById(id);

        if(server.isPresent()) {
            serverRepository.deleteById(id);
            return  true;
        }

        return false;
    }

    // Get all servers
    public List<Server> getAllServers() { return serverRepository.findAll(); }
}
