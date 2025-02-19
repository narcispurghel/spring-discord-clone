package com.discordclone.api.repository;

import com.discordclone.api.entity.Server;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ServerRepository extends JpaRepository<Server, UUID> {

}
