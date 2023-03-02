package fr.univcotedazur.simpletcfs.cli.model;

import java.util.UUID;

public record PaymentDTO(double price, UUID shop, String creditCard) {
}
