package com.discordclone.api.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.*;


@Entity
@Table(name = "users")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, name = "profile_id")
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, length = 100, nullable = false)
    private String email;

    @Column(name = "profile_image")
    private String imageUrl;

    @Column(nullable = false)
    private String password;

    @JsonManagedReference
    @OneToMany(mappedBy = "profile")
    private Set<Server> server = new HashSet<>();

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @OneToMany(mappedBy = "profile")
    private Collection<Member> member;

    public String getImageUrl() {
        return imageUrl;
    }

    public Profile setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Collection<Member> getMember() {
        return member;
    }

    public void setMember(Collection<Member> member) {
        this.member = member;
    }

    public UUID getId() {
        return id;
    }

    public Profile setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Profile setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Profile setEmail(String email) {
        this.email = email;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Profile setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Profile setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Profile setPassword(String password) {
        this.password = password;
        return this;
    }

    public Set<Server> getServer() {
        return server;
    }

    public Profile setServer(Set<Server> server) {
        this.server = server;
        return this;
    }
}