package fr.univcotedazur.simpletcfs.exceptions;

public class NegativePaymentException extends Exception{

        public NegativePaymentException(String message) {
            super(message);
        }

        public NegativePaymentException() {
            super("An error occured during the payment, you can't pay a negative amount");
        }
}
