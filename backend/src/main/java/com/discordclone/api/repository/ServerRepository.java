package com.discordclone.api.repository;

import com.discordclone.api.entity.Server;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerRepository extends JpaRepository<Server, UUID> {
    boolean existsByName(String name);

    List<Server> getAllByMembers_Id(UUID memberId);
}
