package fr.univcotedazur.simpletcfs.cucumber;

import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.entities.Euro;
import fr.univcotedazur.simpletcfs.exceptions.NegativeEuroBalanceException;
import fr.univcotedazur.simpletcfs.exceptions.NegativePaymentException;
import fr.univcotedazur.simpletcfs.exceptions.PaymentException;
import fr.univcotedazur.simpletcfs.interfaces.Bank;
import fr.univcotedazur.simpletcfs.interfaces.ChargeCard;
import fr.univcotedazur.simpletcfs.repositories.CustomerRepository;
import io.cucumber.java.Before;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Etantdonné;
import io.cucumber.java.fr.Quand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ChargeCardStepDef {

    @Autowired
    ChargeCard chargeCard;

    @Autowired
    CustomerRepository customerRepository;

    private Customer customer;

    private Exception exception;


    @Etantdonné("la balance du client {int} euros \\(card)")
    public void laBalanceDuClientNbEurosCardEurosCard(int amount) {
        customer = new Customer("customer");
        try {
            customer.getCustomerBalance().addEuro(new Euro(amount));
        } catch (NegativeEuroBalanceException e) {
            exception = e;
        }
    }

    @Quand("le client paye un montant {int} en euro \\(card)")
    public void leClientPayeUnMontantNbEurosEnEuroCard(int amount) {
        try {
            chargeCard.chargeCard(new Euro(amount), customer, "896983");
        } catch (NegativePaymentException | PaymentException e) {
            exception = e;
        }
    }

    @Alors("la balance du client contient {int} euros")
    public void laBalanceDuClientContientNbEurosCardNewEuros(int amount) {
        Euro amountEuro = new Euro(amount);
        System.out.println(customer.getCustomerBalance());
        System.out.println(customer.getId());
        System.out.println(customerRepository.findById(customer.getId()));
        assertEquals(amountEuro, customer.getCustomerBalance().getEuroBalance());
        assertEquals(amountEuro, customerRepository.findById(customer.getId()).map(customer -> customer.getCustomerBalance().getEuroBalance()).orElse(null));
    }

    @Alors("il y a une NegativePaymentException")
    public void ilYAUneErreur() {
        assertEquals(NegativePaymentException.class, exception.getClass());
    }
}
