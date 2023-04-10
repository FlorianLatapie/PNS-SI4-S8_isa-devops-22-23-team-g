package fr.univcotedazur.mfc.interfaces;

import java.util.Date;
import java.util.List;

import fr.univcotedazur.mfc.entities.*;
import fr.univcotedazur.mfc.exceptions.*;

public interface Payment {
    EuroTransaction payWithCreditCard(Euro amount, Customer customer, Shop shop, String creditCard, Date date) throws PaymentException, NegativePaymentException;
    EuroTransaction payWithCreditCard(Euro amount, List<AdvantageItem> advantages, Shop shop, Customer customer, String creditCard, Date date) throws PaymentException, NegativePaymentException, CustomerDoesntHaveAdvantageException, ParkingException;


    EuroTransaction payWithAccountMoney(Euro amount, Customer customer, Shop shop, Date date) throws EuroBalanceException;
    EuroTransaction payWithAccountMoney(Euro amount, List<AdvantageItem> advantages, Customer customer, Shop shop, Date date) throws EuroBalanceException;

    EuroTransaction payWithLoyaltyCard(Euro amount, Customer customer, Shop shop, Date date) throws NegativePaymentException, NegativeEuroBalanceException;
}
