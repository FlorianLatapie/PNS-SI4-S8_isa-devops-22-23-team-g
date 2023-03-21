package fr.univcotedazur.simpletcfs.controllers.dto.payement;

import fr.univcotedazur.simpletcfs.controllers.dto.CustomerDTO;

public record EuroTransactionDTO(
        CustomerDTO customerDTO,
        double price,
        String shopName,
        Long idTransaction,
        int pointEarned) {
}
