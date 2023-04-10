package fr.univcotedazur.mfc.exceptions;

import java.util.Date;

import fr.univcotedazur.mfc.entities.Customer;
import fr.univcotedazur.mfc.entities.Euro;

public class PaymentException extends Exception{

    public PaymentException(String message) {
        super(message);
    }

    public PaymentException(Euro amount, Customer customer, Date date) {
        super("An error occurred during the payment of " + amount + " by " + customer + " on " + date);
    }
}