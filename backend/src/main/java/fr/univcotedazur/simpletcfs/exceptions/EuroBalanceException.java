package fr.univcotedazur.simpletcfs.exceptions;

public class EuroBalanceException extends Exception {
    public EuroBalanceException(String message) {
        super(message);
    }

    public EuroBalanceException() {
        super("The consumer doesn't have enough money");
    }

}
