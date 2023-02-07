package fr.univcotedazur.simpletcfs.exceptions;

import java.util.UUID;

public class UnknownOrderId extends Exception {

    private UUID orderId;

    public UnknownOrderId(UUID id) {
        orderId = id;
    }

    public UnknownOrderId() {
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }
}