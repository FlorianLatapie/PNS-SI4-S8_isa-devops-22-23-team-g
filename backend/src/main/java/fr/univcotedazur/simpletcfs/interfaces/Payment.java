package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.*;
import fr.univcotedazur.simpletcfs.exceptions.*;

import java.util.Date;
import java.util.List;

public interface Payment {
    EuroTransaction payWithCreditCard(Euro amount, Customer customer, Shop shop, String creditCard, Date date) throws PaymentException, NegativePaymentException;
    EuroTransaction payWithCreditCard(Euro amount, List<AdvantageItem> advantages, Shop shop, Customer customer, String creditCard, Date date) throws PaymentException, NegativePaymentException, CustomerDoesntHaveAdvantageException;


    EuroTransaction payWithAccountMoney(Euro amount, Customer customer, Shop shop, Date date) throws EuroBalanceException;
    EuroTransaction payWithAccountMoney(Euro amount, List<AdvantageItem> advantages, Customer customer, Shop shop, Date date) throws EuroBalanceException;

    EuroTransaction payWithLoyaltyCard(Euro amount, Customer customer, Shop shop, Date date) throws NegativePaymentException, NegativeEuroBalanceException;
}
