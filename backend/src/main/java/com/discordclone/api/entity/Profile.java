package com.discordclone.api.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "profile_id", nullable = false)
    private UUID id;

    @Column(unique = true, length = 100, nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(name = "profile_image")
    private String imageUrl;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "profile", cascade = {CascadeType.PERSIST}, orphanRemoval = true)
    private List<Member> members = new ArrayList<>();

    @Column(updatable = false, name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();;

    public Profile() {
    }

    public Profile(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
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
