package fr.univcotedazur.simpletcfs.cucumber;

import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.entities.Euro;
import fr.univcotedazur.simpletcfs.exceptions.CustomerAlreadyExistsException;
import fr.univcotedazur.simpletcfs.exceptions.NegativeEuroBalanceException;
import fr.univcotedazur.simpletcfs.exceptions.NegativePaymentException;
import fr.univcotedazur.simpletcfs.exceptions.PaymentException;
import fr.univcotedazur.simpletcfs.interfaces.Bank;
import fr.univcotedazur.simpletcfs.interfaces.ChargeCard;
import fr.univcotedazur.simpletcfs.interfaces.CustomerModifier;
import fr.univcotedazur.simpletcfs.repositories.CustomerRepository;
import io.cucumber.java.Before;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Etantdonné;
import io.cucumber.java.fr.Quand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import javax.transaction.Transactional;
import java.util.Optional;

@SpringBootTest
@Transactional
public class ChargeCardStepDef {

    @Autowired
    ChargeCard chargeCard;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerModifier customerModifier;

    private String customerUsername;

    private Exception exception;

    @Autowired
    private Bank bankMock;

    @Before
    public void setUp() {
        customerRepository.deleteAll();
        // when(bankMock.pay(anyString(), any(Euro.class)))
        //         .thenAnswer(invocation -> ((String) invocation.getArgument(0)).contains("896983"));

        try {
            Customer res = customerModifier.signup("customer", null);
            System.out.println("le client créé " + res);
        } catch (CustomerAlreadyExistsException e) {
            throw new RuntimeException(e);
        }
    }


    @Etantdonné("la balance du client {int} euros \\(card)")
    public void laBalanceDuClientNbEurosCardEurosCard(int amount) throws NegativeEuroBalanceException {
        Optional<Customer> resOptional = customerRepository.findCustomerByUsername("customer");
        if (resOptional.isPresent()) {
            Customer customer = resOptional.get();
            customer.getCustomerBalance().addEuro(new Euro(amount));
            customerUsername = customer.getUsername();
        } else {
            System.out.println("Le client récupéré de la DB est vide");
            throw new RuntimeException("Customer not found");
        }
    }

    @Quand("le client paye un montant {int} en euro \\(card)")
    public void leClientPayeUnMontantNbEurosEnEuroCard(int amount) {
        try {
            Optional<Customer> resOptional = customerRepository.findCustomerByUsername(customerUsername);
            if(resOptional.isPresent()){
                Customer customer = resOptional.get();
                chargeCard.chargeCard(new Euro(amount), customer, "896983");
            }
        } catch (NegativePaymentException | PaymentException e) {
            exception = e;
        }
    }

    @Alors("la balance du client contient {int} euros")
    public void laBalanceDuClientContientNbEurosCardNewEuros(int amount) {
        Euro amountEuro = new Euro(amount);

            Optional<Customer> resOptional = customerRepository.findCustomerByUsername(customerUsername);
            if(resOptional.isPresent()){
                Customer customer = resOptional.get();
                assertEquals(amountEuro, customer.getCustomerBalance().getEuroBalance());
                assertEquals(amountEuro, customerRepository.findById(customer.getId()).map(foundCustomer -> foundCustomer.getCustomerBalance().getEuroBalance()).orElse(null));
            }
    }

    @Alors("il y a une NegativePaymentException")
    public void ilYAUneErreur() {
        assertEquals(NegativePaymentException.class, exception.getClass());
    }
}
