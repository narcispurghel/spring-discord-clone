package com.discordclone.api.template;

import java.util.Date;
import java.util.UUID;

public class RegisterResponse {
    private UUID id;

    private String name;

    private String imageUrl;

    private String email;

    private Date createdAt;

    private Date updatedAt;

    public RegisterResponse() {
        this.imageUrl = "";
    }

    public UUID getId() {
        return id;
    }

    public RegisterResponse setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public RegisterResponse setName(String name) {
        this.name = name;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public RegisterResponse setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public RegisterResponse setEmail(String email) {
        this.email = email;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public RegisterResponse setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public RegisterResponse setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
