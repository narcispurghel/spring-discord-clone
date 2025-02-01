package com.discordclone.api.model;

import com.discordclone.api.util.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private UUID id;

    @Column(name = "member_role")
    private Role role = Role.ADMIN;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;

    @ManyToMany(mappedBy = "members")
    @JsonManagedReference
    private Set<Server> servers = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "profile_id")
    @JsonBackReference
    private Profile profile;

    public Set<Server> getServers() {
        return servers;
    }

    public Member setServers(Set<Server> servers) {
        this.servers = servers;
        return this;
    }

    public UUID getId() {
        return id;
    }

    public Member setId(UUID id) {
        this.id = id;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public Member setRole(Role role) {
        this.role = role;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Member setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Member setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public Profile getProfile() {
        return profile;
    }

    public Member setProfile(Profile profile) {
        this.profile = profile;
        return this;
    }
}
