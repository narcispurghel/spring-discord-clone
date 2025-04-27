package com.discordclone.api.repository;

import com.discordclone.api.entity.Channel;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRepository extends JpaRepository<Channel, UUID> {
}
