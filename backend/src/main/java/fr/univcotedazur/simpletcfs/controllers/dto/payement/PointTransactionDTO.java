package fr.univcotedazur.simpletcfs.controllers.dto.payement;

import fr.univcotedazur.simpletcfs.controllers.dto.CustomerDTO;

import java.util.UUID;

public record PointTransactionDTO(CustomerDTO customerDTO,String avantageName, int price, Long idTransaction) {
}