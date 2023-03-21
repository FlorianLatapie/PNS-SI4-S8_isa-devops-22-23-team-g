package fr.univcotedazur.simpletcfs.cli.model;

public record EuroTransactionDTO(
        CliCustomer customerDTO,
        double price,
        String shopName,
        Long idTransaction,
        int pointsEarned) {
}
