package fr.univcotedazur.mfc.cucumber;

import io.cucumber.java.Before;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Etantdonné;
import io.cucumber.java.fr.Quand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fr.univcotedazur.mfc.entities.Customer;
import fr.univcotedazur.mfc.entities.Euro;
import fr.univcotedazur.mfc.exceptions.*;
import fr.univcotedazur.mfc.interfaces.Bank;
import fr.univcotedazur.mfc.interfaces.ChargeCard;
import fr.univcotedazur.mfc.interfaces.CustomerFinder;
import fr.univcotedazur.mfc.interfaces.CustomerModifier;
import fr.univcotedazur.mfc.repositories.CustomerRepository;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
public class ChargeCardStepDef {

    public static String MAGIC_CARD_NUMBER = "896983";

    @Autowired
    ChargeCard chargeCard;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerModifier customerModifier;

    @Autowired
    CustomerFinder customerFinder;

    private String customerUsername;

    private Exception exception;

    @Autowired
    private Bank bankMock;

    @Before
    public void setUp() {
        customerRepository.deleteAll();
        when(bankMock.pay(anyString(), any(Euro.class))).thenAnswer(invocation -> {
            String cardNumber = invocation.getArgument(0);
            Euro amount = invocation.getArgument(1);
            return cardNumber.contains(MAGIC_CARD_NUMBER) && amount.getCentsAmount() >= 0;
        });
    }


    @Etantdonné("la balance du client {int} euros \\(card)")
    public void laBalanceDuClientNbEurosCardEurosCard(int amount) throws NegativeEuroBalanceException, CustomerAlreadyExistsException {
        customerUsername = "customer";
        Customer customer = customerModifier.signup(customerUsername, null);
        customer.getCustomerBalance().addEuro(new Euro(amount));
        System.out.println("le client a été trouvé " + customer);
    }

    @Quand("le client paye un montant {int} en euro \\(card)")
    public void leClientPayeUnMontantNbEurosEnEuroCard(int amount) {
        try {
            Customer customer = customerFinder.login(customerUsername);
            chargeCard.chargeCard(new Euro(amount), customer, "896983", null);
        } catch (NegativePaymentException | PaymentException e) {
            exception = e;
        } catch (CustomerNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Alors("la balance du client contient {int} euros")
    public void laBalanceDuClientContientNbEurosCardNewEuros(int amount) {
        Euro amountEuro = new Euro(amount);

        Customer customer;
        try {
            customer = customerFinder.login(customerUsername);
        } catch (CustomerNotFoundException e) {
            throw new RuntimeException(e);
        }

        assertEquals(amountEuro, customer.getCustomerBalance().getEuroBalance());
        assertEquals(amountEuro, customerRepository.findById(customer.getId()).map(foundCustomer -> foundCustomer.getCustomerBalance().getEuroBalance()).orElse(null));
    }

    @Alors("il y a une NegativePaymentException")
    public void ilYAUneErreur() {
        assertEquals(NegativePaymentException.class, exception.getClass());
    }
}
