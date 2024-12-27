package com.discordclone.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    private Profile profileId;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @OneToMany(mappedBy = "server")
    @JsonManagedReference
    Set<Channel> channels = new HashSet<>();


    @ManyToMany
    @JoinTable(
            name = "member_server",
            joinColumns = @JoinColumn(name = "server_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    @JsonBackReference
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public Server setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Server setUpdatedAt(Date updatedAt) {
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

    public Profile getProfileId() {
        return profileId;
    }

    public Server setProfileId(Profile profileId) {
        this.profileId = profileId;
        return this;
    }
}