package com.discordclone.api.dto;

import java.util.Date;
import java.util.UUID;

public class ProfileDTO {
    private UUID id;
    private String name;
    private String email;
    private String imageUrl;
    private Date createdAt;
    private Date updatedAt;

    public UUID getId() {
        return id;
    }

    public ProfileDTO setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProfileDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ProfileDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ProfileDTO setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public ProfileDTO setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public ProfileDTO setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
