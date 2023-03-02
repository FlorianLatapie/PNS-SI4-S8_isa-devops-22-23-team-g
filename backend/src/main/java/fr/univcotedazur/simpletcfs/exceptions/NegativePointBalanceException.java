package fr.univcotedazur.simpletcfs.exceptions;

public class NegativePointBalanceException extends Exception{
    public NegativePointBalanceException(String message) {
        super(message);
    }

    public NegativePointBalanceException() {
        super("The customer point balance is negative");
    }
}
