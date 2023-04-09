package fr.univcotedazur.mfc.cucumber;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Et;
import io.cucumber.java.fr.Etantdonné;
import io.cucumber.java.fr.Quand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fr.univcotedazur.mfc.components.advantages.AdvantageCashier;
import fr.univcotedazur.mfc.components.advantages.AdvantageManager;
import fr.univcotedazur.mfc.components.advantages.ExchangePoint;
import fr.univcotedazur.mfc.components.registry.AdvantageCatalogRegistry;
import fr.univcotedazur.mfc.entities.*;
import fr.univcotedazur.mfc.exceptions.*;
import fr.univcotedazur.mfc.interfaces.*;
import fr.univcotedazur.mfc.repositories.AdvantageCatalogRepository;
import fr.univcotedazur.mfc.repositories.CustomerRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
public class AdvantagePaymentTest {


    String MAGIC_PARK_NUMBER = "896983";
    @Autowired
    AdvantageAdder advantageAdder;

    @Autowired
    AdvantageCashier advantageCashier;
    @Autowired
    AdvantageManager advantageManager;
    @Autowired
    ExchangePoint pointPayement;
    @Autowired
    AdvantageCatalogRegistry advantageCatalogRegistry;
    @Autowired
    AdvantageCatalogRepository advantageCatalogRepository;
    @Autowired
    CustomerFinder customerFinder;
    @Autowired
    CustomerModifier customerModifier;
    @Autowired
    CustomerRepository customerRepository;
    Exception exception;
    Shop shop;
    @Autowired
    Payment payment;
    private String customerName;
    private CustomerBalance customerBalance;
    @Autowired
    private Park parkMock;

    @Before
    public void setUp() {
        when(parkMock.parkForFree(anyString())).thenAnswer(invocation -> {
            String licensePlate = invocation.getArgument(0);
            return licensePlate.contains(MAGIC_PARK_NUMBER);
        });

        customerName = "test";
    }


    @Etantdonné("un invité ayant {int} points de fidélité")
    public void unInvitéAyantPointsDeFidélité(int arg0) throws CustomerAlreadyExistsException {
        customerBalance = new CustomerBalance(new Point(arg0), new ArrayList<>(), new Euro(0));
        Customer customer = customerModifier.signup(customerName, null);
        customer.setCustomerBalance(customerBalance);
    }

    @Quand("le client achète un produit coûtant {int} points de fidélité")
    public void leClientAchèteUnProduitCoûtantPointsDeFidélité(int price) throws NegativePointBalanceException, CustomerNotFoundException {
        Customer customer = customerRepository.findCustomerByUsername(customerName).get();
        AdvantageItem advantage = advantageCatalogRegistry.addNewAdvantage("Cucumber", new Point(price), null, "Beautiful Cucumber", Status.CLASSIC);

        advantageManager.addAdvantage(customer, advantage);

        pointPayement.payPoints(customer, advantage);
    }

    @Alors("le client a cet avantage")
    public void leClientACetAvantage() throws CustomerNotFoundException {
        Customer customer = customerRepository.findCustomerByUsername(customerName).get();
        if (customer.getAdvantageItems().isEmpty())
            fail();
        else
            assertNotEquals(0, customer.getAdvantageItems().size());
    }

    @Et("le client a {int} points de fidélité")
    public void leClientAPointsDeFidélité(int nbPoints) throws CustomerNotFoundException {
        Customer customer = customerRepository.findCustomerByUsername(customerName).get();
        assertEquals(nbPoints, customer.getCustomerBalance().getPointBalance().getPointAmount());
    }

    @Alors("le client n'a pas cet avantage")
    public void leClientNAPasCetAvantage() throws CustomerNotFoundException {
        Customer customer = customerRepository.findCustomerByUsername(customerName).get();
        assertEquals(0, customer.getAdvantageItems().size());

    }

    @Quand("le client achète un produit trop cher pour lui coûtant {int} points de fidélité")
    public void leClientAchèteUnProduitTropCherPourLuiCoûtantPointsDeFidélité(int price) throws NegativePointBalanceException, CustomerNotFoundException {
        Customer customer = customerRepository.findCustomerByUsername(customerName).get();
        AdvantageItem advantage = advantageCatalogRegistry.addNewAdvantage("Very Very expensive Cucumber", new Point(price), null, "Beautiful and Expensive Cucumber", Status.CLASSIC);
        assertThrows(NegativePointBalanceException.class, () -> pointPayement.payPoints(customer, advantage));
    }


    //-----------------Scenario 3----------------- le client dépense son avantage

    @Etantdonné("un client possèdant un avantage")
    public void un_client_possèdant_un_avantage() throws CustomerAlreadyExistsException {
        Customer customer = customerModifier.signup(customerName, null);
        AdvantageItem advantage = advantageCatalogRegistry.addNewAdvantage("advantageCucumber", new Point(0), null, "Beautiful and Expensive Cucumber", Status.CLASSIC);
        customerBalance = new CustomerBalance(new Point(0), new ArrayList<>(Collections.singletonList(advantage)), new Euro(0));
        customer.setCustomerBalance(customerBalance);
    }

    @Quand("le client dépense son avantage")
    public void le_client_dépense_son_avantage() throws CustomerDoesntHaveAdvantageException, PaymentException, NegativePaymentException, ParkingException, CustomerNotFoundException {
        Customer customer = customerRepository.findCustomerByUsername(customerName).get();
        AdvantageItem advantage = advantageCatalogRegistry.findByTitle("advantageCucumber").get();
        advantageCashier.debitAdvantage(customer, advantage);
    }

    @Alors("le client n'a plus cet avantage")
    public void le_client_n_a_plus_cet_avantage() throws CustomerNotFoundException {
        Customer customer = customerRepository.findCustomerByUsername(customerName).get();
        AdvantageItem advantage = advantageCatalogRegistry.findByTitle("advantageCucumber").get();
        assertFalse(customer.getCustomerBalance().getAdvantageItem().contains(advantage));
    }

    @Etantdonné("un client possèdant plusieurs avantage")
    public void unClientPossèdantPlusieursAvantage() throws CustomerAlreadyExistsException {
        AdvantageItem advantage = advantageCatalogRegistry.addNewAdvantage("advantageCucumber1", new Point(0), null, "Beautiful and Expensive Cucumber", Status.CLASSIC);
        AdvantageItem advantage2 = advantageCatalogRegistry.addNewAdvantage("advantageCucumber2", new Point(0), null, "Beautiful and Expensive Cucumber", Status.CLASSIC);
        customerBalance = new CustomerBalance(new Point(0), new ArrayList<>(Arrays.asList(advantage, advantage2)), new Euro(0));
        Customer customer = customerModifier.signup(customerName, null);
        customer.setCustomerBalance(customerBalance);
    }

    @Quand("le client dépense tout ses avantages")
    public void leClientDépenseToutSesAvantages() throws CustomerDoesntHaveAdvantageException, ParkingException, CustomerNotFoundException {
        Customer customer = customerRepository.findCustomerByUsername(customerName).get();
        AdvantageItem advantage = advantageCatalogRegistry.findByTitle("advantageCucumber1").get();
        AdvantageItem advantage2 = advantageCatalogRegistry.findByTitle("advantageCucumber2").get();
        advantageCashier.debitAdvantage(customer, advantage);
        advantageCashier.debitAdvantage(customer, advantage2);
    }

    @Alors("le client n'a plus ces avantages")
    public void leClientNAPlusCesAvantages() throws CustomerNotFoundException {
        Customer customer = customerRepository.findCustomerByUsername(customerName).get();
        assertEquals(0, customer.getCustomerBalance().getAdvantageItem().size());
    }

    @Etantdonné("un client ne possèdant pas un avantage")
    public void unClientNePossèdantPasUnAvantage() throws CustomerNotFoundException, CustomerAlreadyExistsException {
        customerBalance = new CustomerBalance(new Point(0), new ArrayList<>(), new Euro(0));
        advantageCatalogRegistry.addNewAdvantage("advantageCucumber", new Point(0), null, "Beautiful and Expensive Cucumber", Status.CLASSIC);
        Customer customer = customerModifier.signup(customerName, null);
        customer.setCustomerBalance(customerBalance);
    }

    @Quand("le client essaye de dépenser son avantage")
    public void leClientEssayeDeDépenserSonAvantage() throws CustomerNotFoundException {
        Customer customer = customerRepository.findCustomerByUsername(customerName).get();
        AdvantageItem advantage = advantageCatalogRegistry.findByTitle("advantageCucumber").get();
        try {
            advantageCashier.debitAdvantage(customer, advantage);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Alors("le systeme revoie une AdvantageNotInBalanceException")
    public void leSystemeRevoieUneAdvantageNotInBalanceException() {
        assertEquals(CustomerDoesntHaveAdvantageException.class, exception.getClass());
    }

    @After
    public void tearDown() {
        customerRepository.deleteAll();
        advantageCatalogRepository.deleteAll();
    }
}
