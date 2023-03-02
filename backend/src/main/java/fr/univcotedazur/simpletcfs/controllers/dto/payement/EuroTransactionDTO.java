package fr.univcotedazur.simpletcfs.controllers.dto.payement;

import fr.univcotedazur.simpletcfs.controllers.dto.CustomerDTO;

import java.util.UUID;

public record EuroTransactionDTO(
        CustomerDTO customerDTO,
        double price,
        String shopName,
        UUID idTransaction,
        int pointEarned) {
}
