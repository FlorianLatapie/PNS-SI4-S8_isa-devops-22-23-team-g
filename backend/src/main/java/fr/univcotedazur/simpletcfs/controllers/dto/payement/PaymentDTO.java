package fr.univcotedazur.simpletcfs.controllers.dto.payement;

public record PaymentDTO(double price, Long shop, String creditCard) {
}