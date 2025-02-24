package com.discordclone.api.dto.auth;

import java.util.Date;
import java.util.UUID;

public class RegisterResponseDto {
    private UUID id;

    private String name;

    private String imageUrl;

    private String email;

    private Date createdAt;

    private Date updatedAt;

    public RegisterResponseDto() {
        this.imageUrl = "";
    }

    public UUID getId() {
        return id;
    }

    public RegisterResponseDto setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public RegisterResponseDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public RegisterResponseDto setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public RegisterResponseDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public RegisterResponseDto setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public RegisterResponseDto setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
