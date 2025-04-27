package com.discordclone.api.entity;

import com.discordclone.api.util.Role;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id", nullable = false)
    private UUID id;

    @Column(name = "member_role")
    private Role role = Role.MEMBER;

    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    @ManyToOne
    @JoinColumn(name = "server_id", nullable = false)
    private Server server;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    public Member() {

    }

    public Member(Role role, Profile profile, Server server) {
        this.server = server;
        this.role = role;
        this.profile = profile;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
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
}
