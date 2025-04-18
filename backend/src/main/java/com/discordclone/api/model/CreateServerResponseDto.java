package com.discordclone.api.model;

import java.util.Date;
import java.util.UUID;

public class CreateServerResponseDto {
    private UUID id;
    private String name;
    private String imageUrl;
    private UUID inviteCode;
    private String profileId;
    private Date createdAt;
    private Date updatedAt;

    public CreateServerResponseDto() {

    }

    public UUID getId() {
        return id;
    }

    public CreateServerResponseDto setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CreateServerResponseDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public CreateServerResponseDto setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public UUID getInviteCode() {
        return inviteCode;
    }

    public CreateServerResponseDto setInviteCode(UUID inviteCode) {
        this.inviteCode = inviteCode;
        return this;
    }

    public String getProfileId() {
        return profileId;
    }

    public CreateServerResponseDto setProfileId(String profileId) {
        this.profileId = profileId;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public CreateServerResponseDto setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public CreateServerResponseDto setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
