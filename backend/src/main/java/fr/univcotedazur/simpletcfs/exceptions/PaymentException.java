package fr.univcotedazur.simpletcfs.exceptions;

public class PaymentException extends Exception{

    public PaymentException(String message) {
        super(message);
    }

    public PaymentException() {
        super("An error occured during the payment");
    }
}