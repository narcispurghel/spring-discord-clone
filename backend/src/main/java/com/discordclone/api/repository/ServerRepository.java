package com.discordclone.api.repository;

import com.discordclone.api.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface ServerRepository extends JpaRepository<Server, UUID> {
    Optional<Server> findByName(String serverName);

    Set<Server> findAllAsSetByProfile_Id(UUID profileID);
}
