package fr.univcotedazur.simpletcfs.controllers.dto.payement;

public record PaymentDTO(double price, int pointPrice, Long shop, String creditCard) {
}