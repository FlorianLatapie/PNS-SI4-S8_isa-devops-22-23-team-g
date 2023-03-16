package fr.univcotedazur.simpletcfs.cucumber;

import fr.univcotedazur.simpletcfs.components.AdvantageCashier;
import fr.univcotedazur.simpletcfs.components.AdvantageManager;
import fr.univcotedazur.simpletcfs.components.ExchangePoint;
import fr.univcotedazur.simpletcfs.entities.*;
import fr.univcotedazur.simpletcfs.exceptions.CustomerDoesntHaveAdvantageException;
import fr.univcotedazur.simpletcfs.exceptions.NegativePointBalanceException;
import fr.univcotedazur.simpletcfs.interfaces.AdvantageAdder;
import fr.univcotedazur.simpletcfs.repositories.CustomerRepository;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Et;
import io.cucumber.java.fr.Etantdonné;
import io.cucumber.java.fr.Quand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class AdvantagePaymentTest {

    @Autowired
    AdvantageAdder advantageAdder;

    @Autowired
    AdvantageCashier advantageCashier;

    private Customer customer;

    private CustomerBalance customerBalance;
    @Autowired
    AdvantageManager advantageManager;
    @Autowired
    ExchangePoint pointPayement;

    AdvantageItem advantage;
    AdvantageItem advantage2;

    Exception exception;



    @Etantdonné("un invité ayant {int} points de fidélité")
    public void unInvitéAyantPointsDeFidélité(int arg0) {
            customerBalance = new CustomerBalance(new Point(arg0), new ArrayList<>(),new Euro(0));
            customer = new Customer("test");
            customer.setCustomerBalance(customerBalance);
        }

    @Quand("le client achète un produit coûtant {int} points de fidélité")
    public void leClientAchèteUnProduitCoûtantPointsDeFidélité(int price) throws NegativePointBalanceException {
        advantage = new AdvantageItem(Status.CLASSIC,"Cucumber","Beautiful Cucumber" ,new Point(price),null);
        advantageManager.addAdvantage(customer, advantage);

        pointPayement.payPoints(customer, advantage);
    }

    @Alors("le client a cet avantage")
    public void leClientACetAvantage() {
        if (customer.getAdvantageItems().isEmpty())
            fail();
        else
            assertNotEquals(0, customer.getAdvantageItems().size());
    }

    @Et("le client a {int} points de fidélité")
    public void leClientAPointsDeFidélité(int nbPoints) {
        assertEquals(nbPoints, customer.getCustomerBalance().getPointBalance().getPointAmount());
    }

    @Alors("le client n'a pas cet avantage")
    public void leClientNAPasCetAvantage() {
        assertEquals(0,customer.getAdvantageItems().size());

    }

    @Quand("le client achète un produit trop cher pour lui coûtant {int} points de fidélité")
    public void leClientAchèteUnProduitTropCherPourLuiCoûtantPointsDeFidélité(int price) throws NegativePointBalanceException {
        advantage = new AdvantageItem(Status.CLASSIC,"Very Very expensive Cucumber","Beautiful and Expensive Cucumber" ,new Point(price),null);
        assertThrows(NegativePointBalanceException.class, () -> pointPayement.payPoints(customer, advantage));
    }


    //-----------------Scenario 3----------------- le client dépense son avantage

    @Etantdonné("un client possèdant un avantage")
    public void un_client_possèdant_un_avantage() {
        advantage = mock(AdvantageItem.class);
        customerBalance = new CustomerBalance(new Point(0), new ArrayList<>(Arrays.asList(advantage)),new Euro(0));
        customer = new Customer("test");
        customer.setCustomerBalance(customerBalance);
    }

    @Quand("le client dépense son avantage")
    public void le_client_dépense_son_avantage() throws CustomerDoesntHaveAdvantageException {
        advantageCashier.debitAdvantage(customer, advantage);
    }
    @Alors("le client n'a plus cet avantage")
    public void le_client_n_a_plus_cet_avantage() {
        assertEquals(false,customer.getCustomerBalance().getAdvantageItem().contains(advantage));
    }

    @Etantdonné("un client possèdant plusieurs avantage")
    public void unClientPossèdantPlusieursAvantage() {
        advantage = mock(AdvantageItem.class);
        advantage2 = mock(AdvantageItem.class);
        customerBalance = new CustomerBalance(new Point(0), new ArrayList<>(Arrays.asList(advantage,advantage2)),new Euro(0));
        customer = new Customer("test");
        customer.setCustomerBalance(customerBalance);
    }

    @Quand("le client dépense tout ses avantages")
    public void leClientDépenseToutSesAvantages() throws CustomerDoesntHaveAdvantageException {
        advantageCashier.debitAdvantage(customer, advantage);
        advantageCashier.debitAdvantage(customer, advantage2);
    }

    @Alors("le client n'a plus ces avantages")
    public void leClientNAPlusCesAvantages() {
        assertEquals(0,customer.getCustomerBalance().getAdvantageItem().size());
    }

    @Etantdonné("un client ne possèdant pas un avantage")
    public void unClientNePossèdantPasUnAvantage() {
        customerBalance = new CustomerBalance(new Point(0), new ArrayList<>(),new Euro(0));
        customer = new Customer("test");
        customer.setCustomerBalance(customerBalance);
    }

    @Quand("le client essaye de dépenser son avantage")
    public void leClientEssayeDeDépenserSonAvantage() {
        System.out.println((customer.getAdvantageItems()));
        try {
            advantageCashier.debitAdvantage(customer, advantage);
            System.out.println("pip");
        } catch (Exception e) {
            System.out.println("pop");
            exception = e;
        }
    }

    @Alors("le systeme revoie une AdvantageNotInBalanceException")
    public void leSystemeRevoieUneAdvantageNotInBalanceException() {
        assertEquals(CustomerDoesntHaveAdvantageException.class, exception.getClass());
    }
}
