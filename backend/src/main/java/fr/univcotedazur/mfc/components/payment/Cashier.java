package fr.univcotedazur.mfc.components.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import fr.univcotedazur.mfc.components.advantages.AdvantageCashier;
import fr.univcotedazur.mfc.components.advantages.StatusUpdater;
import fr.univcotedazur.mfc.components.registry.EuroTransactionRegistry;
import fr.univcotedazur.mfc.connectors.BankProxy;
import fr.univcotedazur.mfc.entities.*;
import fr.univcotedazur.mfc.exceptions.*;
import fr.univcotedazur.mfc.interfaces.ChargeCard;
import fr.univcotedazur.mfc.interfaces.Payment;
import fr.univcotedazur.mfc.interfaces.StatusModifier;

import java.util.Date;
import java.util.List;

@Transactional
@Component
public class Cashier implements Payment, ChargeCard {
    private AdvantageCashier advantageCashier;
    private BankProxy bankProxy;
    private PointsRewards pointsRewards;

    private EuroTransactionRegistry euroTransactionRegistry;

    private StatusModifier statusUpdater;

    @Autowired
    public Cashier(AdvantageCashier advantageCashier, BankProxy bankProxy, PointsRewards pointsRewards, EuroTransactionRegistry euroTransactionRegistry, StatusUpdater statusUpdater) {
        this.advantageCashier = advantageCashier;
        this.bankProxy = bankProxy;
        this.pointsRewards = pointsRewards;
        this.euroTransactionRegistry = euroTransactionRegistry;
        this.statusUpdater = statusUpdater;
    }

    @Override
    public EuroTransaction payWithCreditCard(Euro amount, Customer customer, Shop shop, String creditCard, Date date) throws PaymentException, NegativePaymentException {
        if(amount.getCentsAmount() < 0){
            throw new NegativePaymentException();
        }

        Point pointEarned;
        if (bankProxy.pay(creditCard,amount)){
            // on ajoute 2 fois le montant d'euro en point, (les euros sont en centimes)
            pointEarned = pointsRewards.gain(customer, amount);
        }else{
            throw new PaymentException(amount, customer, date);
        }

        // need to register the transaction in DB

        EuroTransaction euroTransaction = new EuroTransaction(customer, shop, amount, pointEarned, date);
        euroTransactionRegistry.add(euroTransaction);
        statusUpdater.updateStatus(customer, euroTransaction);
        return euroTransaction;
    }

    @Override
    public EuroTransaction payWithLoyaltyCard(Euro amount, Customer customer, Shop shop, Date date) throws NegativePaymentException, NegativeEuroBalanceException {
        if (amount.getCentsAmount() < 0) {
            throw new NegativePaymentException();
        }
        // points earned are 2 times the amount of euro, euros are in cents
        customer.getCustomerBalance().removeEuro(amount);
        Point pointEarned = pointsRewards.gain(customer, amount);

        // need to register the transaction in BD
        EuroTransaction euroTransaction = new EuroTransaction(customer, shop, amount, pointEarned, date);
        euroTransactionRegistry.add(euroTransaction);
        return euroTransaction;
    }

    @Override
    public EuroTransaction payWithCreditCard(Euro amount, List<AdvantageItem> advantages, Shop shop, Customer customer, String creditCard, Date date) throws PaymentException, NegativePaymentException, CustomerDoesntHaveAdvantageException, ParkingException {
        EuroTransaction euroTransaction = payWithCreditCard(amount, customer, shop, creditCard, date);
        advantageCashier.debitAllAdvantage(customer, advantages);
        return euroTransaction;
    }

    @Override
    public EuroTransaction payWithAccountMoney(Euro amount, Customer customer, Shop shop, Date date) throws EuroBalanceException {
        return new EuroTransaction(customer, shop, amount, date);
    }


    @Override
    public EuroTransaction payWithAccountMoney(Euro amount, List<AdvantageItem> advantages, Customer customer, Shop shop, Date date) throws EuroBalanceException {
        return new EuroTransaction(customer, shop, amount, date);
    }

    @Override
    public EuroTransaction chargeCard(Euro amount, Customer customer, String creditCard, Date date) throws NegativePaymentException, PaymentException {
        if(amount.getCentsAmount() < 0){
            throw new NegativePaymentException();
        }
        if (!bankProxy.pay(creditCard, amount)) {
            throw new PaymentException(amount, customer, date);
        }

        customer.getCustomerBalance().setEuroBalance(customer.getCustomerBalance().getEuroBalance().add(amount));

        return new EuroTransaction(customer, amount, date);
    }
}
