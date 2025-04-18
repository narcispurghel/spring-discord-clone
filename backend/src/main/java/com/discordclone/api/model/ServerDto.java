package com.discordclone.api.model;

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

    public ServerDto() {}

    public ServerDto(UUID id,
                     String name,
                     String imageUrl,
                     UUID inviteCode,
                     UUID profileId,
                     LocalDateTime createdAt,
                     LocalDateTime updatedAt,
                     Set<ChannelDto> channels,
                     Set<MemberDto> members) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.inviteCode = inviteCode;
        this.profileId = profileId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.channels = channels;
        this.members = members;
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

    public UUID getProfileId() {
        return profileId;
    }

    public void setProfileId(UUID profileId) {
        this.profileId = profileId;
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

    public Set<ChannelDto> getChannels() {
        return channels;
    }

    public void setChannels(Set<ChannelDto> channels) {
        this.channels = channels;
    }

    public Set<MemberDto> getMembers() {
        return members;
    }

    public void setMembers(Set<MemberDto> members) {
        this.members = members;
    }
}
