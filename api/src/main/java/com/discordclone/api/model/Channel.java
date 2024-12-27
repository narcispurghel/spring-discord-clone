package com.discordclone.api.model;

import com.discordclone.api.util.ChannelType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

@Entity
@Transactional
public class Channel {

    @Id
    @Column(name = "channel_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "channel_name")
    private String channelName = "general";

    @Column
    private ChannelType type = ChannelType.TEXT;

    @Column(name = "channel_server_id")
    private UUID serverId;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "server_id")
    @JsonBackReference
    private Server server;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id")
    @JsonBackReference
    private Profile profile;

    public Channel() {
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

    public UUID getServerId() {
        return serverId;
    }

    public Channel setServerId(UUID serverId) {
        this.serverId = serverId;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Channel setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Channel setUpdatedAt(Date updatedAt) {
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

    public Profile getProfile() {
        return profile;
    }

    public Channel setProfile(Profile profile) {
        this.profile = profile;
        return this;
    }
}
