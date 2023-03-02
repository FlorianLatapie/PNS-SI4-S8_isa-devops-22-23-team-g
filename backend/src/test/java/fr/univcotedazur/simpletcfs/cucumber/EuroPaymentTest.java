package fr.univcotedazur.simpletcfs.cucumber;

import fr.univcotedazur.simpletcfs.connectors.BankProxy;
import fr.univcotedazur.simpletcfs.entities.*;
import fr.univcotedazur.simpletcfs.exceptions.NegativePaymentException;
import fr.univcotedazur.simpletcfs.exceptions.PaymentException;
import fr.univcotedazur.simpletcfs.interfaces.Bank;
import fr.univcotedazur.simpletcfs.interfaces.Payment;
import fr.univcotedazur.simpletcfs.interfaces.PointAdder;
import io.cucumber.java.Before;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Etantdonné;
import io.cucumber.java.fr.Quand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class EuroPaymentTest {



    @Autowired
    PointAdder pointAdder;

    @Autowired
    Payment payment;

    private Shop shop;

    private Customer customer;

    private Exception exception;

    @Autowired // Spring/Cucumber bug workaround: autowired the mock declared in the Config class
    private BankProxy bankMock;


    @Before
    public void setUp(){
        // Mock the bank
        when(bankMock.pay(any(String.class), any(Euro.class))).thenReturn(true);
    }

    @Etantdonné("un client")
    public void un_client() {
        customer = new Customer("test");
        shop = mock(Shop.class);
    }

    @Quand("le client paye un montant {int} en euro")
    public void le_client_paye_un_montant_en_euro(Integer int1) {
        try{
            // x100 car la l'attribut de euro est en centimes
            payment.payWithCreditCard(new Euro(int1 * 100), customer, shop, "plop");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Alors("il recois {int} en point")
    public void il_recois_en_point(Integer int1) {
        assertEquals(new Point(int1).pointAmount(), customer.getCustomerBalance().getPointBalance().pointAmount());
    }


    //-------------------------------------------------------------------------------- Paiement négatif

    @Quand("le client paye un montant négatif {int} en euro")
    public void le_client_paye_un_montant_négatif_en_euro(Integer int1) {
        try{
            // x100 car la l'attribut de euro est en centimes
            payment.payWithCreditCard(new Euro(int1 * 100), customer, shop, "896983");
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
        customer.getCustomerBalance().setEuroBalance(new Euro(int1 * 100));
    }

    @Quand("le client paye un montant {int} en euro avec sa carte de fidélité")
    public void le_client_paye_un_montant_en_euro_avec_sa_carte_de_fidélité(Integer int1) {
        try{
            // x100 car la l'attribut de euro est en centimes
            payment.payWithLoyaltyCard(new Euro(int1 * 100), customer, shop);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Alors("la carte reste {int}")
    public void la_carte_reste(Integer int1) {
        assertEquals(new Euro(int1 * 100).centsAmount(), customer.getCustomerBalance().getEuroBalance().centsAmount());
    }


}
