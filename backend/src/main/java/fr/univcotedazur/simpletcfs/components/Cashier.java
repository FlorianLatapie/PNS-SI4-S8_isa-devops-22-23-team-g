package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.connectors.BankProxy;
import fr.univcotedazur.simpletcfs.entities.*;
import fr.univcotedazur.simpletcfs.exceptions.EuroBalanceException;
import fr.univcotedazur.simpletcfs.exceptions.NegativeEuroBalanceException;
import fr.univcotedazur.simpletcfs.exceptions.NegativePaymentException;
import fr.univcotedazur.simpletcfs.exceptions.PaymentException;
import fr.univcotedazur.simpletcfs.interfaces.ChargeCard;
import fr.univcotedazur.simpletcfs.interfaces.Payment;
import fr.univcotedazur.simpletcfs.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Cashier implements Payment, ChargeCard {

    private AdvantageCashier advantageCashier;
    private BankProxy bankProxy;
    private PointsRewards pointsRewards;

    private CustomerRepository customerRepository;

    @Autowired
    public Cashier(AdvantageCashier advantageCashier, BankProxy bankProxy, PointsRewards pointsRewards, CustomerRepository customerRepository) {
        this.advantageCashier = advantageCashier;
        this.bankProxy = bankProxy;
        this.pointsRewards = pointsRewards;
        this.customerRepository = customerRepository;
    }

    // TODO: merge the two methods below if possible
    @Override
    public EuroTransaction payWithCreditCard(Euro amount, Customer customer, Shop shop, String creditCard) throws PaymentException, NegativePaymentException {
        if (amount.centsAmount() < 0) {
            throw new NegativePaymentException();
        }

        Point pointEarned;
        if (bankProxy.pay(creditCard, amount)) {
            // points earned are 2 times the amount of euro, euros are in cents
            pointEarned = pointsRewards.gain(customer, new Point((amount.centsAmount() / 100) * 2));
        } else {
            throw new PaymentException();
        }

        // need to register the transaction in DB
        return new EuroTransaction(customer, shop, amount, pointEarned);
    }

    @Override
    public EuroTransaction payWithLoyaltyCard(Euro amount, Customer customer, Shop shop) throws NegativePaymentException, NegativeEuroBalanceException {
        if (amount.centsAmount() < 0) {
            throw new NegativePaymentException();
        }
        // points earned are 2 times the amount of euro, euros are in cents
        customer.getCustomerBalance().removeEuro(amount);
        pointsRewards.gain(customer, new Point((amount.centsAmount() / 100) * 2));

        // need to register the transaction in BD
        return new EuroTransaction(customer, shop, amount);
    }

    @Override
    public EuroTransaction payWithCreditCard(Euro amount, List<AdvantageItem> advantages, Shop shop, Customer customer, String creditCard) throws PaymentException, NegativePaymentException {
        EuroTransaction euroTransaction = payWithCreditCard(amount, customer, shop, creditCard);
        // TODO : need to use the advantages cashier to debit the advantages of the consumer
        return euroTransaction;
    }

    //TODO Write the function
    @Override
    public EuroTransaction payWithAccountMoney(Euro amount, Customer customer, Shop shop) throws EuroBalanceException {
        return new EuroTransaction(customer, shop, amount);
    }

    //TODO Write the function
    @Override
    public EuroTransaction payWithAccountMoney(Euro amount, List<AdvantageItem> advantages, Customer customer, Shop shop) throws EuroBalanceException {
        return new EuroTransaction(customer, shop, amount);
    }

    // TODO: rename chargeCard to reloadCard
    @Override
    public EuroTransaction chargeCard(Euro amount, Customer customer, String creditCard) throws NegativePaymentException, PaymentException {
        if (amount.centsAmount() < 0) {
            throw new NegativePaymentException();
        }
        if (!bankProxy.pay(creditCard, amount)) {
            throw new PaymentException();
        }

        // amount is already checked to be positive above, can't throw exception but need to catch it
        try {
            customer.getCustomerBalance().addEuro(amount);
        } catch (Exception e) {
            System.err.println("Error while adding (" + amount + ") euro to customer balance, this should not happen");
            e.printStackTrace();
        }

        customerRepository.save(customer, customer.getId());

        return new EuroTransaction(customer, amount);
    }
}
