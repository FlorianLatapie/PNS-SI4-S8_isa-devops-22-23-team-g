package fr.univcotedazur.simpletcfs.exceptions;

import java.util.UUID;

public class CustomerIdNotFoundException extends Exception {

    private UUID id;

    public CustomerIdNotFoundException(UUID id) {
        this.id = id;
    }

    public CustomerIdNotFoundException() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
