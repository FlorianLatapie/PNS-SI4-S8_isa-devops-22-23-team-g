package fr.univcotedazur.simpletcfs.controllers.dto.payement;

import fr.univcotedazur.simpletcfs.controllers.dto.CustomerDTO;

import java.util.UUID;

public record AdvantageTransactionDTO(CustomerDTO customerDTO,String advantageName, UUID idTransaction) {
}