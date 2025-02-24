package com.discordclone.api.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ServerDto {
    private UUID id;
    private String name;
    private String imageUrl;
    private UUID inviteCode;
    private UUID profileId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<ChannelDto> channels = new HashSet<>();
    private Set<MemberDto> members = new HashSet<>();

    public ServerDto() {
    }

    public UUID getId() {
        return id;
    }

    public ServerDto setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ServerDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ServerDto setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public UUID getInviteCode() {
        return inviteCode;
    }

    public ServerDto setInviteCode(UUID inviteCode) {
        this.inviteCode = inviteCode;
        return this;
    }

    public UUID getProfileId() {
        return profileId;
    }

    public ServerDto setProfileId(UUID profileId) {
        this.profileId = profileId;
        return this;
    }

    public Set<ChannelDto> getChannels() {
        return channels;
    }

    public ServerDto setChannels(Set<ChannelDto> channels) {
        this.channels = channels;
        return this;
    }

    public Set<MemberDto> getMembers() {
        return members;
    }

    public ServerDto setMembers(Set<MemberDto> members) {
        this.members = members;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public ServerDto setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public ServerDto setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
