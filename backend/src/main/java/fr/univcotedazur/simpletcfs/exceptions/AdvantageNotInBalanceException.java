package fr.univcotedazur.simpletcfs.exceptions;

public class AdvantageNotInBalanceException extends Exception{
    public AdvantageNotInBalanceException(String message) {
        super(message);
    }

    public AdvantageNotInBalanceException() {
        super("the customer doesn't have this advantage");
    }
}
