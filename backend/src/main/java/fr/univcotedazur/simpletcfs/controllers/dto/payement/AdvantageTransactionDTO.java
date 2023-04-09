package fr.univcotedazur.simpletcfs.controllers.dto.payement;

import fr.univcotedazur.simpletcfs.controllers.dto.CustomerDTO;

public record AdvantageTransactionDTO(CustomerDTO customerDTO, AdvantageItemDTO advantage) {
}