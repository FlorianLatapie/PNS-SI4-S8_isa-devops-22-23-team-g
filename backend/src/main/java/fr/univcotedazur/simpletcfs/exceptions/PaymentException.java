package fr.univcotedazur.simpletcfs.exceptions;

import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.entities.Euro;

import java.util.Date;

public class PaymentException extends Exception{

    public PaymentException(String message) {
        super(message);
    }

    public PaymentException(Euro amount, Customer customer, Date date) {
        super("An error occurred during the payment of " + amount + " by " + customer + " on " + date);
    }
}