package fr.univcotedazur.simpletcfs.cucumber.ordering;

import fr.univcotedazur.simpletcfs.entities.Customer;
import fr.univcotedazur.simpletcfs.exceptions.AlreadyExistingCustomerException;
import fr.univcotedazur.simpletcfs.exceptions.EmptyCartException;
import fr.univcotedazur.simpletcfs.exceptions.PaymentException;
import fr.univcotedazur.simpletcfs.interfaces.Bank;
import fr.univcotedazur.simpletcfs.interfaces.CartProcessor;
import fr.univcotedazur.simpletcfs.interfaces.CustomerRegistration;
import fr.univcotedazur.simpletcfs.repositories.CustomerRepository;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;

public class BadOrdering {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerRegistration customerRegistration;

    @Autowired
    private CartProcessor cartProcessor;

    @Autowired // Spring/Cucumber bug workaround: autowired the mock declared in the Config class
    private Bank bankMock;

    private Customer badBoy;
    private boolean validationRefused;

    @Before
    public void settingUpContext() throws PaymentException {
        customerRepository.deleteAll();
        when(bankMock.pay(any(Customer.class), anyDouble())).thenReturn(true);
    }

    @Given("a bad customer")
    public void theBadCustomer() throws AlreadyExistingCustomerException {
        badBoy = customerRegistration.register("BadBoy", "dummy card");
    }

    @When("He validates an empty cart")
    public void validatesAnEmptyCart() {
        try {
            cartProcessor.validate(badBoy);
        } catch (EmptyCartException e) {
            validationRefused = true;
            return;
        } catch (PaymentException e) {
        }
        validationRefused = false;
    }

    @Then("the order is not created")
    public void theOrderIsNotCreated() {
        assertTrue(validationRefused);
    }

}
