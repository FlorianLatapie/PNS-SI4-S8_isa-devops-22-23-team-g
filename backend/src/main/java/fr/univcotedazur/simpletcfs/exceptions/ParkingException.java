package fr.univcotedazur.simpletcfs.exceptions;

public class ParkingException extends Exception {
    public ParkingException(String message) {
        super(message);
    }

    public ParkingException() {
        super("An error occurred with parking API");
    }
}
