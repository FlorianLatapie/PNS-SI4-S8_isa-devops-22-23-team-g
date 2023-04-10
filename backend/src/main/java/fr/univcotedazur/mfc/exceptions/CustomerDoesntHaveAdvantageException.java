package fr.univcotedazur.mfc.exceptions;

public class CustomerDoesntHaveAdvantageException extends Exception {
    public CustomerDoesntHaveAdvantageException(String advantageName) {
        super("the customer doesn't have this advantage: " + advantageName);
    }

    public CustomerDoesntHaveAdvantageException() {
        super("the customer doesn't have this advantage");
    }
}
