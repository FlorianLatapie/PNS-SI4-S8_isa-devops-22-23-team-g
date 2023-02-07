package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.OrderStatus;
import fr.univcotedazur.simpletcfs.exceptions.UnknownOrderId;

import java.util.UUID;

public interface Tracker {

    OrderStatus retrieveStatus(UUID orderId) throws UnknownOrderId;

}