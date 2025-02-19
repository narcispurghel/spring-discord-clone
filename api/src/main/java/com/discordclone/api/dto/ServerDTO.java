package com.discordclone.api.dto;

import com.discordclone.api.entity.Channel;
import com.discordclone.api.entity.Member;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ServerDTO {
    private UUID id;
    private String name;
    private String imageUrl;
    private UUID inviteCode;
    private UUID profileId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<Channel> channels = new HashSet<>();
    private Set<Member> members = new HashSet<>();

    public ServerDTO() {
    }

    public UUID getId() {
        return id;
    }

    public ServerDTO setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ServerDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ServerDTO setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public UUID getInviteCode() {
        return inviteCode;
    }

    public ServerDTO setInviteCode(UUID inviteCode) {
        this.inviteCode = inviteCode;
        return this;
    }

    public UUID getProfileId() {
        return profileId;
    }

    public ServerDTO setProfileId(UUID profileId) {
        this.profileId = profileId;
        return this;
    }

    public Set<Channel> getChannels() {
        return channels;
    }

    public ServerDTO setChannels(Set<Channel> channels) {
        this.channels = channels;
        return this;
    }

    public Set<Member> getMembers() {
        return members;
    }

    public ServerDTO setMembers(Set<Member> members) {
        this.members = members;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public ServerDTO setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public ServerDTO setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
