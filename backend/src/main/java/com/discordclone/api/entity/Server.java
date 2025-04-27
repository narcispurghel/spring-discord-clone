package com.discordclone.api.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "servers")
public class Server {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false)
    private UUID id;

    @Column(unique = true, length = 100, name = "server_name", nullable = false)
    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(nullable = false, name = "invite_code", unique = true)
    private UUID inviteCode = UUID.randomUUID();

    @Column(updatable = false, name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @OneToMany(mappedBy = "server", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Member> members = new ArrayList<>();

    @OneToMany(mappedBy = "server", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Channel> channels = new ArrayList<>();

    public Server() {
    }

    public Server(String name) {
        this.name = name;
    }

    public Server(String name, String imageUrl, UUID inviteCode, LocalDateTime updatedAt, List<Member> members, List<Channel> channels) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.inviteCode = inviteCode;
        this.updatedAt = updatedAt;
        this.members = members;
        this.channels = channels;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public UUID getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(UUID inviteCode) {
        this.inviteCode = inviteCode;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public List<Channel> getChannels() {
        return channels;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }
}
