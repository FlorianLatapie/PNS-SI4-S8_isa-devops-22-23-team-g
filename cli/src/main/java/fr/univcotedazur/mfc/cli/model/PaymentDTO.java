package fr.univcotedazur.mfc.cli.model;


public record PaymentDTO(double price, int pointPrice, Long shop, String creditCard) {
}
