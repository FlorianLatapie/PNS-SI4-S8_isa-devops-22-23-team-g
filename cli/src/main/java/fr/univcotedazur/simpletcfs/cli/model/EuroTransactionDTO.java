package fr.univcotedazur.simpletcfs.cli.model;

import java.util.UUID;

public record EuroTransactionDTO(CliCustomer customerDTO, double price, String shopName, UUID idTransaction, int pointEarned) {

}
