package fr.univcotedazur.mfc.controllers.dto.payement;

import fr.univcotedazur.mfc.controllers.dto.CustomerDTO;

public record AdvantageTransactionDTO(CustomerDTO customerDTO, AdvantageItemDTO advantage) {
}