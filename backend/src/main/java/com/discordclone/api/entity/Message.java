package com.discordclone.api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String content;

    public Message() {
        // Explicit no args constructor for JPA
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
