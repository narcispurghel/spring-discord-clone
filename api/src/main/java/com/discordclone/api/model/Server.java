package com.discordclone.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Table(name = "servers", schema = "public")
@Entity
public class Server {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    private UUID id;

    @Column(unique = true, length = 100, name = "server_name")
    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(nullable = false, name = "invite_code")
    private UUID inviteCode;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id")
    @JsonBackReference
    private Profile profile;

    @Column(updatable = false, name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @OneToMany(mappedBy = "server")
    private Set<Channel> channels = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "member_server",
            joinColumns = @JoinColumn(name = "server_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    private Set<Member> members = new HashSet<>();

    public UUID getId() {
        return id;
    }

    public Server setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Server setName(String name) {
        this.name = name;
        return this;
    }

    public UUID getInviteCode() {
        return inviteCode;
    }

    public Server setInviteCode(UUID inviteCode) {
        this.inviteCode = inviteCode;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Server setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Server setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Server setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public Set<Member> getMembers() {
        return members;
    }

    public Server setMembers(Set<Member> members) {
        this.members = members;
        return this;
    }

    public Set<Channel> getChannels() {
        return channels;
    }

    public Server setChannels(Set<Channel> channels) {
        this.channels = channels;
        return this;
    }

    public void addChannel(Channel channel) {
        channels.add(channel);
    }

    public void addMember(Member member) {
        members.add(member);
    }

    public Profile getProfile() {
        return profile;
    }

    public Server setProfile(Profile profile) {
        this.profile = profile;
        return this;
    }
}