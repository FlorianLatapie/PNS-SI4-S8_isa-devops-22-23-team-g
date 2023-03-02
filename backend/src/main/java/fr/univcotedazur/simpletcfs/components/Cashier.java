package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.connectors.BankProxy;
import fr.univcotedazur.simpletcfs.entities.*;
import fr.univcotedazur.simpletcfs.exceptions.EuroBalanceException;
import fr.univcotedazur.simpletcfs.exceptions.NegativeEuroBalanceException;
import fr.univcotedazur.simpletcfs.exceptions.NegativePaymentException;
import fr.univcotedazur.simpletcfs.exceptions.PaymentException;
import fr.univcotedazur.simpletcfs.interfaces.Payment;
import fr.univcotedazur.simpletcfs.interfaces.ChargeCard;
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

    @Override
    public EuroTransaction payWithCreditCard(Euro amount, Customer customer, Shop shop, String creditCard) throws PaymentException, NegativePaymentException {
        if(amount.centsAmount() < 0){
            throw new NegativePaymentException();
        }

        Point pointEarned;
        if (bankProxy.pay(creditCard,amount)){
            // on ajoute 2 fois le montant d'euro en point, (les euros sont en centimes)
            pointEarned = pointsRewards.gain(customer, new Point((amount.centsAmount() / 100 ) * 2));
        }else{
            throw new PaymentException();
        }

        // need to register the transaction in BD
        return new EuroTransaction(customer, shop, amount, pointEarned);
    }

    @Override
    public EuroTransaction payWithCreditCard(Euro amount, List<AdvantageItem> advantages, Shop shop, Customer customer, String creditCard) throws PaymentException, NegativePaymentException {
        EuroTransaction euroTransaction = payWithCreditCard(amount, customer, shop, creditCard);
        // need to use the advantages cashier to debit the advantages of the consumer
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

    @Override
    public EuroTransaction payWithLoyaltyCard(Euro amount, Customer customer, Shop shop) throws NegativePaymentException, NegativeEuroBalanceException {
        if(amount.centsAmount() < 0){
            throw new NegativePaymentException();
        }
        // on ajoute 2 fois le montant d'euro en point, (les euros sont en centimes)
        customer.getCustomerBalance().removeEuro(amount);
        pointsRewards.gain(customer, new Point((amount.centsAmount() / 100 ) * 2));

        // need to register the transaction in BD
        return new EuroTransaction(customer, shop, amount);
    }


    @Override
    public EuroTransaction chargeCard(Euro amount, Customer customer, String creditCard) throws NegativePaymentException, PaymentException {
        if(amount.centsAmount() < 0){
            throw new NegativePaymentException();
        }
        if (! bankProxy.pay(creditCard, amount)){
            throw new PaymentException();
        }

        try {
            customer.getCustomerBalance().addEuro(amount);
        } catch (Exception e) {
            e.printStackTrace();
        }

        customerRepository.save(customer, customer.getId());

        return new EuroTransaction(customer, amount);
    }
}
