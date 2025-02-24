package com.discordclone.api.repository;

import com.discordclone.api.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface ServerRepository extends JpaRepository<Server, UUID> {
    Optional<Server> findByName(String serverName);

    @Query(value = "SELECT * FROM servers WHERE profile_id = :profileId", nativeQuery = true)
    Set<Server> findAllAsSetByProfileId(@Param("profileId") UUID profileID);
}
