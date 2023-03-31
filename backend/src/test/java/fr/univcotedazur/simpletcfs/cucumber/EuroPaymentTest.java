package fr.univcotedazur.simpletcfs.cucumber;

import fr.univcotedazur.simpletcfs.connectors.BankProxy;
import fr.univcotedazur.simpletcfs.entities.*;
import fr.univcotedazur.simpletcfs.exceptions.CustomerAlreadyExistsException;
import fr.univcotedazur.simpletcfs.exceptions.NegativePaymentException;
import fr.univcotedazur.simpletcfs.exceptions.PaymentException;
import fr.univcotedazur.simpletcfs.interfaces.CustomerFinder;
import fr.univcotedazur.simpletcfs.interfaces.CustomerModifier;
import fr.univcotedazur.simpletcfs.interfaces.Payment;
import fr.univcotedazur.simpletcfs.interfaces.PointAdder;
import fr.univcotedazur.simpletcfs.repositories.CustomerRepository;
import io.cucumber.java.Before;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Etantdonné;
import io.cucumber.java.fr.Etantdonnéque;
import io.cucumber.java.fr.Quand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import java.util.Calendar;
import java.util.Date;

import static fr.univcotedazur.simpletcfs.cucumber.ChargeCardStepDef.MAGIC_CARD_NUMBER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@SpringBootTest
@Transactional
public class EuroPaymentTest {

    @Autowired
    PointAdder pointAdder;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerModifier customerModifier;

    @Autowired
    CustomerFinder customerFinder;

    @Autowired
    Payment payment;

    private Shop shop;

    private String customerName = "customer";

    private Exception exception;

    private Date date = new Date();

    @Autowired // Spring/Cucumber bug workaround: autowired the mock declared in the Config class
    private BankProxy bankMock;


    @Before
    public void setUp(){
        // Mock the bank
        // customerRepository.deleteAll();
        when(bankMock.pay(anyString(), any(Euro.class))).thenAnswer(invocation -> {
            String cardNumber = invocation.getArgument(0);
            Euro amount = invocation.getArgument(1);
            return cardNumber.contains(MAGIC_CARD_NUMBER) && amount.getCentsAmount() > 0;
        });
    }

    @Etantdonné("un client")
    public void un_client() {
        try {
            Customer res = customerModifier.signup(customerName, null);
            System.out.println("le client créé " + res);
        } catch (CustomerAlreadyExistsException e) {
            throw new RuntimeException(e);
        }
        shop = new Shop("shop", "Paris", new IBAN("1234"));
    }

    @Quand("le client paye un montant {int} en euro")
    public void le_client_paye_un_montant_en_euro(Integer int1) {
        Customer customer = customerRepository.findCustomerByUsername(customerName).get();
        try{
            // x100 car la l'attribut de euro est en centimes
            payment.payWithCreditCard(new Euro(int1 * 100), customer, shop, MAGIC_CARD_NUMBER, date);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Alors("il recois {int} en point")
    public void il_recois_en_point(Integer int1) {
        Customer customer = customerRepository.findCustomerByUsername(customerName).get();
        assertEquals(new Point(int1).getPointAmount(), customer.getCustomerBalance().getPointBalance().getPointAmount());
    }


    //-------------------------------------------------------------------------------- Paiement négatif

    @Quand("le client paye un montant négatif {int} en euro")
    public void le_client_paye_un_montant_négatif_en_euro(Integer int1) {
        Customer customer = customerRepository.findCustomerByUsername(customerName).get();
        try{
            // x100 car la l'attribut de euro est en centimes
            payment.payWithCreditCard(new Euro(int1 * 100), customer, shop, MAGIC_CARD_NUMBER, date);
        }catch (NegativePaymentException | PaymentException e){
            exception = e;
        }
    }

    @Alors("le système renvoie une NegativePaymentException")
    public void leSystèmeRenvoieUneNegativePaymentException() {
        assertEquals(NegativePaymentException.class, exception.getClass());
    }




    //-------------------------------------------------------------------------------- Paiement avec carte de fidélité

    @Etantdonné("la balance du client {int} euros")
    public void la_balance_du_client_euros(Integer int1) {
        Customer customer = customerRepository.findCustomerByUsername(customerName).get();
        customer.getCustomerBalance().setEuroBalance(new Euro(int1 * 100));
    }

    @Quand("le client paye un montant {int} en euro avec sa carte de fidélité")
    public void le_client_paye_un_montant_en_euro_avec_sa_carte_de_fidélité(Integer int1) {
        Customer customer = customerRepository.findCustomerByUsername(customerName).get();
        try{
            // x100 car la l'attribut de euro est en centimes
            payment.payWithLoyaltyCard(new Euro(int1 * 100), customer, shop, date);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Alors("la carte reste {int}")
    public void la_carte_reste(Integer int1) {
        Customer customer = customerRepository.findCustomerByUsername(customerName).get();
        assertEquals(new Euro(int1 * 100).getCentsAmount(), customer.getCustomerBalance().getEuroBalance().getCentsAmount());
    }

    @Etantdonnéque("le client a payé la semaine dernière")
    public void leClientAPayéLaSemaineDernière() {
        // reset customer
        Customer customer = customerRepository.findCustomerByUsername(customerName).get();
        customer.setStatus(Status.CLASSIC);
        customer.setLastEuroTransactionDate(new Date(0));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, -5);
        Date previousDate = calendar.getTime();

        try {
            payment.payWithCreditCard(new Euro(100), customer, shop, MAGIC_CARD_NUMBER, previousDate);
        } catch (PaymentException | NegativePaymentException e) {
            exception = e;
        }
    }

    @Quand("le client paye cette semaine")
    public void leClientPayeCetteSemaine() {
        Customer customer = customerRepository.findCustomerByUsername(customerName).get();

        try {
            payment.payWithCreditCard(new Euro(100), customer, shop, MAGIC_CARD_NUMBER, date);
        } catch (PaymentException | NegativePaymentException e) {
            exception = e;
        }
    }

    @Alors("il obtient le statut VFP")
    public void ilObtientLeStatutVFP() {
        Customer customer = customerRepository.findCustomerByUsername(customerName).get();

        assertEquals(Status.VFP, customer.getStatus());
    }

    @Etantdonnéque("le client n'a rien payé récemment")
    public void leClientNARienPayéRécemment() {
        Customer customer = customerRepository.findCustomerByUsername(customerName).get();

        customer.setStatus(Status.CLASSIC);
        customer.setLastEuroTransactionDate(new Date(0));
    }

    @Alors("il reste statut CLASSIC")
    public void ilResteStatutCLASSIC() {
        Customer customer = customerRepository.findCustomerByUsername(customerName).get();

        assertEquals(Status.CLASSIC, customer.getStatus());
    }
}
