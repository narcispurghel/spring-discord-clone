package com.discordclone.api.template;

import java.util.Date;
import java.util.UUID;

public class CreateServerResponse {
    private UUID id;
    private String name;
    private String imageUrl;
    private UUID inviteCode;
    private String profileId;
    private Date createdAt;
    private Date updatedAt;

    public CreateServerResponse() {

    }

    public UUID getId() {
        return id;
    }

    public CreateServerResponse setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CreateServerResponse setName(String name) {
        this.name = name;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public CreateServerResponse setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public UUID getInviteCode() {
        return inviteCode;
    }

    public CreateServerResponse setInviteCode(UUID inviteCode) {
        this.inviteCode = inviteCode;
        return this;
    }

    public String getProfileId() {
        return profileId;
    }

    public CreateServerResponse setProfileId(String profileId) {
        this.profileId = profileId;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public CreateServerResponse setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public CreateServerResponse setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
