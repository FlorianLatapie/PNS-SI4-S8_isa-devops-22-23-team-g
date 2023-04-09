package fr.univcotedazur.mfc.interfaces;

import java.util.Date;

import fr.univcotedazur.mfc.entities.Customer;
import fr.univcotedazur.mfc.entities.Euro;
import fr.univcotedazur.mfc.entities.EuroTransaction;
import fr.univcotedazur.mfc.exceptions.NegativePaymentException;
import fr.univcotedazur.mfc.exceptions.PaymentException;

public interface ChargeCard {
    EuroTransaction chargeCard(Euro amount, Customer customer, String creditCard, Date date) throws NegativePaymentException, PaymentException;
}
