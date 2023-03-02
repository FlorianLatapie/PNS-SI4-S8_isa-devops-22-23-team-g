package fr.univcotedazur.simpletcfs.entities;

import java.util.UUID;

public class Account {
    UUID id;
    String username;

    public Account(String username) {
        this.id = UUID.randomUUID();
        this.username = username;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}