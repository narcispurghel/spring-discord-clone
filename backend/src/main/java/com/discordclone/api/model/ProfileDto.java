package com.discordclone.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ProfileDto {
    @JsonIgnore
    private UUID id;
    private String name;
    private String email;
    private Set<ServerDto> servers = new HashSet<>();
    private String imageUrl;
    private Date createdAt;
    private Date updatedAt;

    public UUID getId() {
        return id;
    }

    public ProfileDto setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProfileDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ProfileDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ProfileDto setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public ProfileDto setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public ProfileDto setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public Set<ServerDto> getServers() {
        return servers;
    }

    public void setServers(Set<ServerDto> servers) {
        this.servers = servers;
    }
}
