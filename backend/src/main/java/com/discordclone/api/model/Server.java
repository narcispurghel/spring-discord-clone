package com.discordclone.api.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Table(name = "servers")
@Entity
public class Server {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    private UUID id;

    @Column(unique = true, length = 100, name = "server_name")
    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(nullable = false, name = "invite_code")
    private UUID inviteCode;

    @Column(updatable = false, name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @OneToMany
    private Set<Channel> channels = new HashSet<>();

    @OneToMany
    private Set<Member> members = new HashSet<>();

    public UUID getId() {
        return id;
    }

    public Server setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Server setName(String name) {
        this.name = name;
        return this;
    }

    public UUID getInviteCode() {
        return inviteCode;
    }

    public Server setInviteCode(UUID inviteCode) {
        this.inviteCode = inviteCode;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Server setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Server setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Server setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public Set<Member> getMembers() {
        return members;
    }

    public Server setMembers(Set<Member> members) {
        this.members = members;
        return this;
    }

    public Set<Channel> getChannels() {
        return channels;
    }

    public Server setChannels(Set<Channel> channels) {
        this.channels = channels;
        return this;
    }

}