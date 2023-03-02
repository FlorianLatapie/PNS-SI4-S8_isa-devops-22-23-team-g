package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.*;
import fr.univcotedazur.simpletcfs.exceptions.EuroBalanceException;
import fr.univcotedazur.simpletcfs.exceptions.NegativeEuroBalanceException;
import fr.univcotedazur.simpletcfs.exceptions.NegativePaymentException;
import fr.univcotedazur.simpletcfs.exceptions.PaymentException;

import java.util.List;

public interface Payment {
    EuroTransaction payWithCreditCard(Euro amount, Customer customer, Shop shop, String creditCard) throws PaymentException, NegativePaymentException;
    EuroTransaction payWithCreditCard(Euro amount, List<AdvantageItem> advantages, Shop shop, Customer customer, String creditCard) throws PaymentException, NegativePaymentException;


    EuroTransaction payWithAccountMoney(Euro amount, Customer customer, Shop shop) throws EuroBalanceException;
    EuroTransaction payWithAccountMoney(Euro amount, List<AdvantageItem> advantages, Customer customer, Shop shop) throws EuroBalanceException;

    EuroTransaction payWithLoyaltyCard(Euro amount, Customer customer, Shop shop) throws NegativePaymentException, NegativeEuroBalanceException;
}
