package com.discordclone.api.entity;

import com.discordclone.api.util.ChannelType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Channel {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "channel_name")
    private String channelName = "general";

    @Column(name = "channel_type")
    private ChannelType type = ChannelType.TEXT;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "server_id")
    private Server server;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    public Channel() {

    }

    public Channel(String channelName, ChannelType type, Server server) {
        this();
        this.channelName = channelName;
        this.type = type;
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

    public void setServer(Server server) {
        this.server = server;
    }
}
