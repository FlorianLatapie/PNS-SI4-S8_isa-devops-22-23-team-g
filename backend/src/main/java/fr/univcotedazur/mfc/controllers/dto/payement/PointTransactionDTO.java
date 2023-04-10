package fr.univcotedazur.mfc.controllers.dto.payement;

import fr.univcotedazur.mfc.controllers.dto.CustomerDTO;

public record PointTransactionDTO(CustomerDTO customerDTO,String avantageName, int price, Long idTransaction) {
}