package com.discordclone.api.model;

import com.discordclone.api.util.ChannelType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Channel {
    @Id
    @Column(name = "channel_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "channel_name")
    private String channelName = "general";

    @Column
    private ChannelType type = ChannelType.TEXT;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "server_id")
    private Server server;

    public Channel() {
        createdAt = LocalDateTime.now();
    }

    public Channel(String channelName, ChannelType type, LocalDateTime updatedAt, Server server){
        this();
        this.channelName = channelName;
        this.type = type;
        this.updatedAt = updatedAt;
        this.server = server;
    }

    public ChannelType getType() {
        return type;
    }

    public Channel setType(ChannelType type) {
        this.type = type;
        return this;
    }

    public UUID getId() {
        return id;
    }

    public Channel setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getChannelName() {
        return channelName;
    }

    public Channel setChannelName(String channelName) {
        this.channelName = channelName;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Channel setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Channel setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public Server getServer() {
        return server;
    }

    public Channel setServer(Server server) {
        this.server = server;
        return this;
    }

}
