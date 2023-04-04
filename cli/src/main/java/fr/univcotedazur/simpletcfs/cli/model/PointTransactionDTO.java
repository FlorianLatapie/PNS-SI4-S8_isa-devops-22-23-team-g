package fr.univcotedazur.simpletcfs.cli.model;

import java.util.UUID;

public record PointTransactionDTO(CliCustomer customerDTO,String avantageName, int price) {
}