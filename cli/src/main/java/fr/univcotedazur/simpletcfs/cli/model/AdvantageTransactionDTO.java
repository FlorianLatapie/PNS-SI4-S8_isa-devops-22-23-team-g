package fr.univcotedazur.simpletcfs.cli.model;

public record AdvantageTransactionDTO(CliCustomer customerDTO, AdvantageItemDTO advantage) {

    @Override
    public String toString() {
        return "AdvantageTransactionDTO[" +
                "customerDTO=" + customerDTO +
                ", advantage=" + advantage.getTitle() +
                ']';
    }
}