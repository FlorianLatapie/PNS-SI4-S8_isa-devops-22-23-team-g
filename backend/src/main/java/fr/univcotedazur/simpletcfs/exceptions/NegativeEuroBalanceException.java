package fr.univcotedazur.simpletcfs.exceptions;

public class NegativeEuroBalanceException extends Exception{
    public NegativeEuroBalanceException(String message) {
        super(message);
    }

    public NegativeEuroBalanceException() {
        super("The customer euro balance is negative");
    }
}
