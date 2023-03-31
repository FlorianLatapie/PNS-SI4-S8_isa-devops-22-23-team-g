package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.entities.Euro;
import fr.univcotedazur.simpletcfs.entities.EuroTransaction;
import fr.univcotedazur.simpletcfs.exceptions.NegativePaymentException;
import fr.univcotedazur.simpletcfs.exceptions.PaymentException;

import java.util.Date;

public interface ChargeCard {
    EuroTransaction chargeCard(Euro amount, Customer customer, String creditCard, Date date) throws NegativePaymentException, PaymentException;
}
