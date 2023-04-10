package fr.univcotedazur.mfc.controllers.dto.payement;

import fr.univcotedazur.mfc.controllers.dto.CustomerDTO;

public record EuroTransactionDTO(
        CustomerDTO customerDTO,
        double price,
        String shopName,
        Long idTransaction,
        int pointEarned) {
}
