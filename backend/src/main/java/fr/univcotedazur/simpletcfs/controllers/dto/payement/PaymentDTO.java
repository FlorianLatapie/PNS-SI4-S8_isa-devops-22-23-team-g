package fr.univcotedazur.simpletcfs.controllers.dto.payement;

import java.util.UUID;

public record PaymentDTO(double price, UUID shop, String creditCard) {
}